package com.tfg.futstats.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueControllerUnitaryTest {
    
    @Mock
    private RestService restService;

    @InjectMocks
    private LeagueController leagueController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLeagues() {
        List<LeagueDTO> mockLeagues = List.of(new LeagueDTO());
        when(restService.findAllLeagues()).thenReturn(mockLeagues);

        ResponseEntity<List<LeagueDTO>> response = leagueController.getLeagues();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockLeagues, response.getBody());
    }

    @Test
    void testGetLeagueById() {
        League mockLeague = new League();
        mockLeague.setId(1);
        when(restService.findLeagueById(1)).thenReturn(Optional.of(mockLeague));

        ResponseEntity<LeagueDTO> response = leagueController.getLeagueById(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetLeagueByName() {
        League mockLeague = new League();
        mockLeague.setId(1);
        mockLeague.setName("Premier League");
        when(restService.findLeagueByName("Premier League")).thenReturn(Optional.of(mockLeague));

        ResponseEntity<LeagueDTO> response = leagueController.getLeagueByName("Premier League");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Premier League", response.getBody().getName());
    }

    @Test
    void testGetTeams() {
        League mockLeague = new League();
        when(restService.findLeagueById(1)).thenReturn(Optional.of(mockLeague));
        List<TeamResponseDTO> mockTeams = List.of(new TeamResponseDTO());
        when(restService.findTeamsByLeague(mockLeague)).thenReturn(mockTeams);

        ResponseEntity<List<TeamResponseDTO>> response = leagueController.getTeams(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockTeams, response.getBody());
    }

    @Test
    void testGetMatchesByLeague() {
        League mockLeague = new League();
        when(restService.findLeagueById(1)).thenReturn(Optional.of(mockLeague));
        List<MatchDTO> mockMatches = List.of(new MatchDTO());
        when(restService.findMatchesByLeague(mockLeague)).thenReturn(mockMatches);

        ResponseEntity<List<MatchDTO>> response = leagueController.getMatchesByLeague(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockMatches, response.getBody());
    }

    @Test
    void testPostLeagues() {
        LeagueDTO leagueDto = new LeagueDTO();
        leagueDto.setId(1);
        League league = new League();
        league.setId(1);

        doNothing().when(restService).createLeague(league);

        ResponseEntity<LeagueDTO> response = leagueController.postLeagues(leagueDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testDeleteLeagues() {
        League mockLeague = new League();
        mockLeague.setId(1);

        when(restService.findLeagueById(1)).thenReturn(Optional.of(mockLeague));

        ResponseEntity<LeagueDTO> response = leagueController.deleteLeagues(1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateLeague() {
        League mockLeague = new League();
        mockLeague.setId(1);
        LeagueDTO leagueDto = new LeagueDTO();

        when(restService.findLeagueById(1)).thenReturn(Optional.of(mockLeague));

        ResponseEntity<LeagueDTO> response = leagueController.putLeagues(null, 1, leagueDto);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).updateLeague(mockLeague, leagueDto);
    }
}
