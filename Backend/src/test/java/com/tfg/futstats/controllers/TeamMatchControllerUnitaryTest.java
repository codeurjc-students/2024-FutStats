package com.tfg.futstats.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

public class TeamMatchControllerUnitaryTest {
    
    @Mock
    private RestService restService;

    @InjectMocks
    private TeamMatchController teamMatchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPointsPerMatch() {
        TeamMatchDTO mockTeamMatch = new TeamMatchDTO();
        mockTeamMatch.setMatchName("Match1");
        mockTeamMatch.setPoints(3);

        List<TeamMatchDTO> mockTeamMatches = List.of(mockTeamMatch);
        
        when(restService.findAllTeamMatchesByTeam(1)).thenReturn(mockTeamMatches);

        List<Map<String, Object>> response = teamMatchController.getPointsPerMatch(1);

        assertEquals(1, response.size());
        assertEquals("Match1", response.get(0).get("matchName"));
        assertEquals(3, response.get(0).get("points"));
    }
}
