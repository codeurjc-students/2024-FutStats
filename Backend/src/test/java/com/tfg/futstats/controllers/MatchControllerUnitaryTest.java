package com.tfg.futstats.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class MatchControllerUnitaryTest {
    
    @Mock
    private RestService restService;

    @InjectMocks
    private MatchController matchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMatches() {
        List<MatchDTO> mockMatches = List.of(new MatchDTO());
        when(restService.findAllMatches()).thenReturn(mockMatches);

        ResponseEntity<List<MatchDTO>> response = matchController.getMatches();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockMatches, response.getBody());
    }

    @Test
    void testGetMatchById() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);
        
        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<MatchDTO> response = matchController.getMatch(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetMatchByName() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);
        
        when(restService.findMatchByName("El Clásico")).thenReturn(Optional.of(mockMatch));

        ResponseEntity<MatchDTO> response = matchController.getMatch("El Clásico");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("El Clásico", response.getBody().getName());
    }

    @Test
    void testGetLeagueOfMatch() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<LeagueDTO> response = matchController.getLeague(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTeam1OfMatch() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<TeamResponseDTO> response = matchController.getTeam1(1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetTeam2OfMatch() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        team2.setLeague(league);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<TeamResponseDTO> response = matchController.getTeam2(1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetPlayerMatches() {
        Match mockMatch = new Match();
        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));
        List<PlayerMatchDTO> mockPlayerMatches = List.of(new PlayerMatchDTO());

        when(restService.findAllPlayerMatchesByMatch(1)).thenReturn(mockPlayerMatches);

        ResponseEntity<List<PlayerMatchDTO>> response = matchController.getPlayerMatch(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPlayerMatches, response.getBody());
    }

    @Test
    void testPostMatches() {
        MatchDTO matchDto = new MatchDTO();
        matchDto.setLeague("LaLiga");
        matchDto.setTeam1("Real Madrid");
        matchDto.setTeam2("FC Barcelona");
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        doNothing().when(restService).createMatch(mockMatch, league, team1, team2);

        when(restService.findLeagueByName("LaLiga")).thenReturn(Optional.of(league));
        when(restService.findTeamByName("Real Madrid")).thenReturn(Optional.of(team1));
        when(restService.findTeamByName("FC Barcelona")).thenReturn(Optional.of(team2));

        ResponseEntity<MatchDTO> response = matchController.postMatches(null, matchDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testDeleteMatches() {
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<MatchDTO> response = matchController.deleteMatches(null, 1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateMatch() {
        MatchDTO matchDto = new MatchDTO();
        League league = new League();
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));

        ResponseEntity<MatchDTO> response = matchController.putMatches(null,1, matchDto);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).updateMatch(eq(mockMatch), eq(matchDto), any(), any(), any());
    }
}
