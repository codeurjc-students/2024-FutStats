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
public class TeamControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetTeam() throws Exception {
        mockMvc.perform(get("/api/v1/teams/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Real Madrid"));

    }

    @Test
    void testGetTeamByName() throws Exception {
        mockMvc.perform(get("/api/v1/teams/name/{name}", "FC Barcelona")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

    }

    @Test
    void testGetLeagueByTeam() throws Exception {
        mockMvc.perform(get("/api/v1/teams/{id}/league", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("LaLiga"));
    }

    @Test
    void testGetPlayersByTeam() throws Exception {
        mockMvc.perform(get("/api/v1/teams/{id}/players", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Vinicius Jr.")))
                .andExpect(jsonPath("$[1].name", is("Antonio Rudiguer")));
    }

    @Test
    void testGetMatchesByTeam() throws Exception {
        mockMvc.perform(get("/api/v1/teams/{id}/matches", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("FC Barcelona-Real Madrid")));
    }
    
    @Test
    @Transactional
    void testCreateTeam() throws Exception {
        String newTeamJson = """
                {
                    "name": "Osasuna",
                    "league": "LaLiga",
                    "nationality": "Española",
                    "president": "Nikkito"
                }
                """;

        mockMvc.perform(post("/api/v1/teams/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTeamJson))
                .andExpect(status().isCreated()) // Verifica que el estado es 201 Created
                .andExpect(header().exists("Location")) // Verifica que se incluye la cabecera Location
                .andExpect(jsonPath("$.name").value("Osasuna")) // Verifica el nombre de la liga
                .andExpect(jsonPath("$.nationality").value("Española")) // Verifica el país
                .andExpect(jsonPath("$.president").value("Nikkito")); // Verifica el año de fundación
    }

    @Test
    @Transactional
    void testUploadImage() throws Exception {
        String fileName = "test-image.png";
        byte[] fileContent = "Test Image Content".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("imageFile", fileName, "no_image.jpg", fileContent);

        // Realizar la llamada MockMvc
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/teams/{id}/image", 1)
                .file(mockFile)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Real Madrid"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(true));
    }

    @Test
    @Transactional
    void testDeleteTeam() throws Exception {
        mockMvc.perform(delete("/api/v1/teams/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Transactional
    void testDeleteImage() throws Exception {
        mockMvc.perform(delete("/api/v1/teams/{id}/image", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(false));
    }

    @Test
    @Transactional
    void testPutTeam() throws Exception {
        String newLeagueJson = """
                {
                    "name": "Real Madrid",
                    "nationality": "Española",
                    "president": "Prueba"
                }
                """;

        mockMvc.perform(put("/api/v1/teams/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Real Madrid"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.president").value("Prueba"));
    }
}
