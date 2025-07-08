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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetUsers() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(get("/api/v1/users/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(21)))
                .andExpect(jsonPath("$[0].name", is("admin")))
                .andExpect(jsonPath("$[1].name", is("user0")));
    }

    @Test
    void testGetUserById() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(get("/api/v1/users/{id}", 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("admin"));
    }

    @Test
    void testAddUserLeague() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(put("/api/v1/users/{id}/leagues/{leagueId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("LaLiga")));
    }

    @Test
    void testDeleteUserLeague() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(delete("/api/v1/users/{id}/leagues/{leagueId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testAddUserTeam() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(put("/api/v1/users/{id}/teams/{teamId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Real Madrid")));
    }

    @Test
    void testDeleteUserTeam() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(delete("/api/v1/users/{id}/teams/{teamId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testAddUserPlayer() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(put("/api/v1/users/{id}/players/{playerId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Vinicius Jr.")));
    }

    @Test
    void testDeleteUserPlayer() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(delete("/api/v1/users/{id}/players/{playerId}", 1, 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    void testUploadImage() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");
        String fileName = "test-image.png";
        byte[] fileContent = "Test Image Content".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("imageFile", fileName, "no_image.jpg", fileContent);

        // Realizar la llamada MockMvc
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/users/{id}/image", 1)
                .file(mockFile)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(true));
    }

    @Test
    @Transactional
    void testDeleteUser() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");

        mockMvc.perform(delete("/api/v1/users/{id}", 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                }))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Transactional
    void testDeleteImage() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");
        mockMvc.perform(delete("/api/v1/users/{id}/image", 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(false));
    }

    @Test
    @Transactional
    void testPutUser() throws Exception {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getUserPrincipal()).thenReturn(() -> "admin");
        String newLeagueJson = """
                {
                    "name": "adminPrueba",
                    "password": "pass",
                    "roles": ["[user]"],
                    "image": false
                }
                """;

        mockMvc.perform(put("/api/v1/users/{id}", 1)
                .with(request -> {
                    request.setUserPrincipal(mockRequest.getUserPrincipal());
                    return request;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("adminPrueba"));
    }
}
