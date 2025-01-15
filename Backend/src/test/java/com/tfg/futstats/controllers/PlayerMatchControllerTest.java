package com.tfg.futstats.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ActiveProfiles("test")
public class PlayerMatchControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetPlayerMatchById() throws Exception {
        mockMvc.perform(get("/api/v1/players/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vinicius Jr."));

    }

    @Test
    void testGetPlayerByName() throws Exception {
        mockMvc.perform(get("/api/v1/players/name/{name}", "Lamine Yamal")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));

    }

    @Test
    void testGetImage() throws Exception {
        mockMvc.perform(get("/api/v1/players/{id}/image", 1)
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk()); // Verifica que el estado sea 200 OK
    }

    @Test
    void testGetLeagueByPlayer() throws Exception {
        mockMvc.perform(get("/api/v1/players/{id}/league", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("LaLiga")));
    }

    @Test
    void testGetTeamByPlayer() throws Exception {
        mockMvc.perform(get("/api/v1/players/{id}/team", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Real Madrid")));
    }

    @Test
    void testGetPlayerMatchers() throws Exception {
        mockMvc.perform(get("/api/v1/players/{id}/playerMatches", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
                //.andExpect(jsonPath("$[0].name", is("Vinicus Jr.")));
    }

    @Test
    @Transactional
    void testCreatePlayer() throws Exception {
        String newLeagueJson = """
                {
                    "name": "Asencio",
                    "team": "Real Madrid",
                    "league": "LaLiga",
                    "nationality": "Española",
                    "position": "delantero"
                }
                """;

        mockMvc.perform(post("/api/v1/players/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(status().isCreated()) // Verifica que el estado es 201 Created
                .andExpect(header().exists("Location")) // Verifica que se incluye la cabecera Location
                .andExpect(jsonPath("$.name").value("Asencio")) // Verifica el nombre de la liga
                .andExpect(jsonPath("$.nationality").value("Española")) // Verifica el país
                .andExpect(jsonPath("$.position").value("delantero")); // Verifica el año de fundación
    }

    @Test
    @Transactional
    void testUploadImage() throws Exception {
        String fileName = "test-image.png";
        byte[] fileContent = "Test Image Content".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("imageFile", fileName, "no_image.jpg", fileContent);

        // Realizar la llamada MockMvc
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/players/{id}/image", 1)
                .file(mockFile)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vinicius Jr."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(true));
    }

    @Test
    @Transactional
    void testDeletePlayer() throws Exception {
        mockMvc.perform(delete("/api/v1/players/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Transactional
    void testDeleteImage() throws Exception {
        mockMvc.perform(delete("/api/v1/players/{id}/image",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(false));
    }

    @Test
    @Transactional
    void testPutPlayuer() throws Exception {
        String newLeagueJson = """
                {
                    "name": "Raul Asencio",
                    "nationality": "Española",
                    "position": "centrocampista"
                }
                """;

        mockMvc.perform(put("/api/v1/players/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Raul Asencio")) // Verifica el nombre de la liga
                .andExpect(jsonPath("$.nationality").value("Española")) // Verifica el país
                .andExpect(jsonPath("$.position").value("centrocampista")); // Verifica el año de fundación
    }
}

