package com.tfg.futstats.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
public class MatchControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetMatchById() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FC Barcelona-Real Madrid"));
    }

    @Test
    void testGetMatchByName() throws Exception {
        mockMvc.perform(get("/api/v1/matches/name/{name}", "FC Barcelona-Real Madrid")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetLeague() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{id}/league", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("LaLiga")));
    }

    @Test
    void testGetTeam1() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{id}/team1", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("FC Barcelona")));
    }

    @Test
    void testGetTeam2() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{id}/team2", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Real Madrid")));
    }

    @Test
    void testGetPlayerMatch() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{id}/playerMatches", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    void testCreateMatch() throws Exception {
        String newLeagueJson = """
                {
                    "league":"LaLiga",
                    "team1":"Real Madrid",
                    "team2": "FC Barcelona"
                }
                """;

        mockMvc.perform(post("/api/v1/matches/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location")) 
                .andExpect(jsonPath("$.league").value("LaLiga"))
                .andExpect(jsonPath("$.name").value("Real Madrid-FC Barcelona"))
                .andExpect(jsonPath("$.place").value("Santiago Bernabéu"));
    }

    @Test
    @Transactional
    void testDeleteMatch() throws Exception {
        mockMvc.perform(delete("/api/v1/matches/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Transactional
    void testPutMatch() throws Exception {
        String newLeagueJson = """
                {
                    "league":"LaLiga",
                    "team1":"FC Barcelona",
                    "team2":"Real Madrid"
                }
                """;

        mockMvc.perform(put("/api/v1/matches/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLeagueJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("FC Barcelona-Real Madrid"));
    }
}
