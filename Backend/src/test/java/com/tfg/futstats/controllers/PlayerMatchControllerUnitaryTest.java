package com.tfg.futstats.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerMatchControllerUnitaryTest {

    @Mock
    private RestService restService;

    @InjectMocks
    private PlayerMatchController playerMatchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlayerMatchById() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatch mockPlayerMatch = new PlayerMatch();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setPlayer(mockPlayer);
        mockPlayerMatch.setMatch(mockMatch);
        
        when(restService.findPlayerMatchById(1)).thenReturn(Optional.of(mockPlayerMatch));

        ResponseEntity<PlayerMatchDTO> response = playerMatchController.getPlayerMatchById(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetGoalsPerMatch() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatchDTO mockPlayerMatch = new PlayerMatchDTO();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setMatchName("Match1");
        mockPlayerMatch.setGoals(3);

        List<PlayerMatchDTO> mockPlayerMatches = List.of(mockPlayerMatch);
        when(restService.findAllPlayerMatchesByPlayer(1)).thenReturn(mockPlayerMatches);

        List<Map<String, Object>> response = playerMatchController.getGoalsPerMatch(1);

        assertEquals(1, response.size());
        assertEquals("Match1", response.get(0).get("matchName"));
        assertEquals(3, response.get(0).get("goals"));
    }

    @Test
    void testGetPlayerFromPlayerMatch() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatch mockPlayerMatch = new PlayerMatch();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setPlayer(mockPlayer);
        mockPlayerMatch.setMatch(mockMatch);

        when(restService.findPlayerMatchById(1)).thenReturn(Optional.of(mockPlayerMatch));

        ResponseEntity<PlayerDTO> response = playerMatchController.getPlayer(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetMatchFromPlayerMatch() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatch mockPlayerMatch = new PlayerMatch();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setPlayer(mockPlayer);
        mockPlayerMatch.setMatch(mockMatch);

        when(restService.findPlayerMatchById(1)).thenReturn(Optional.of(mockPlayerMatch));

        ResponseEntity<MatchDTO> response = playerMatchController.getMatch(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testDeletePlayerMatch() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Vinicius Jr."));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatch mockPlayerMatch = new PlayerMatch();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setPlayer(mockPlayer);
        mockPlayerMatch.setMatch(mockMatch);

        when(restService.findPlayerMatchById(1)).thenReturn(Optional.of(mockPlayerMatch));
        when(restService.findMatchById(1)).thenReturn(Optional.of(mockMatch));
        when(restService.findPlayerByName(mockPlayerMatch.getName())).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerMatch> response = playerMatchController.deletePlayersMatch(1);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).deletePlayerMatch(mockPlayerMatch, mockMatch, mockPlayer);
    }

    @Test
    void testUpdatePlayerMatch() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(1);
        team2.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setName("El Clásico");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team1);
        mockMatch.setLeague(league);
        mockMatch.setTeam1(team1);
        mockMatch.setTeam2(team2);

        PlayerMatch mockPlayerMatch = new PlayerMatch();
        mockPlayerMatch.setId(1);
        mockPlayerMatch.setPlayer(mockPlayer);
        mockPlayerMatch.setMatch(mockMatch);
        PlayerMatchDTO playerMatchDto = new PlayerMatchDTO();

        when(restService.findPlayerMatchById(1)).thenReturn(Optional.of(mockPlayerMatch));
        when(restService.findMatchById(playerMatchDto.getMatch())).thenReturn(Optional.of(mockMatch));
        when(restService.findPlayerByName(playerMatchDto.getName())).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerMatchDTO> response = playerMatchController.putPlayersMatch(null, 1, playerMatchDto);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).updatePlayerMatch(mockPlayerMatch, playerMatchDto, mockMatch, mockPlayer);
    }
}
