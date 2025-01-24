package com.tfg.futstats.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerResponseDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class PlayerControllerUnitaryTest {
    @Mock
    private RestService restService;

    @InjectMocks
    private Playercontroller playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void testGetAllPlayers() {
        List<PlayerResponseDTO> mockPlayers = List.of(new PlayerResponseDTO());
        when(restService.findAllPlayers()).thenReturn(mockPlayers);

        ResponseEntity<List<PlayerResponseDTO>> response = playerController.getAllPlayers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPlayers, response.getBody());
    }

    @Test
    void testGetPlayerById() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerResponseDTO> response = playerController.getPlayer(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetPlayerByName() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerByName("Paco")).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerResponseDTO> response = playerController.getPlayerByName("Paco");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Paco", response.getBody().getName());
    }

    @Test
    void testGetLeagueByPlayer() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<LeagueDTO> response = playerController.getLeagueByPlayer(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTeamByPlayer() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<TeamResponseDTO> response = playerController.getTeamByPlayer(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetPlayerMatches() {
        League league = new League();
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        List<PlayerMatchDTO> mockPlayerMatches = List.of(new PlayerMatchDTO());

        when(restService.findAllPlayerMatchesByPlayer(1)).thenReturn(mockPlayerMatches);

        ResponseEntity<List<PlayerMatchDTO>> response = playerController.getPlayerMatches(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPlayerMatches, response.getBody());
    }

    @Test
    void testPostPlayer() {
        // Configurar el contexto simulado del servlet
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        PlayerDTO playerDto = new PlayerDTO();
        League league = new League();
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        playerDto.setId(1);
        playerDto.setName("Paco");
        playerDto.setLeague("LaLiga");
        playerDto.setTeam("Real Madrid");

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.createPlayer(any(Player.class), eq(league), eq(team))).thenReturn(mockPlayer);
        when(restService.findLeagueByName("LaLiga")).thenReturn(Optional.of(league));
        when(restService.findTeamByName("Real Madrid")).thenReturn(Optional.of(team));

        ResponseEntity<PlayerResponseDTO> response = playerController.postPlayers(null, playerDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testDeletePlayer() {
        League league = new League();
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerDTO> response = playerController.deletePlayers(1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdatePlayer() {
        League league = new League();
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        Player mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName(("Paco"));

        mockPlayer.setLeague(league);
        mockPlayer.setTeam(team);

        PlayerDTO playerDto = new PlayerDTO();
        playerDto.setName("Alcacer");

        when(restService.findPlayerById(1)).thenReturn(Optional.of(mockPlayer));

        ResponseEntity<PlayerResponseDTO> response = playerController.putPlayers(1, playerDto);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).updatePlayer(eq(mockPlayer), eq(playerDto), any(), any());
    }
}
