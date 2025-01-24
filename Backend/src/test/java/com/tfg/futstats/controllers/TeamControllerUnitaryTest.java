package com.tfg.futstats.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.team.TeamCreationDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

public class TeamControllerUnitaryTest {

    @Mock
    private RestService restService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void testGetAllTeams() {
        List<TeamResponseDTO> mockTeams = List.of(new TeamResponseDTO());
        when(restService.findAllTeams()).thenReturn(mockTeams);

        ResponseEntity<List<TeamResponseDTO>> response = teamController.getAllTeams();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockTeams, response.getBody());
    }

    @Test
    void testGetTeamById() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setName("Osasuna");
        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        ResponseEntity<TeamResponseDTO> response = teamController.getTeam(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetTeamByName() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setName("Osasuna");
        mockTeam.setLeague(league);

        when(restService.findTeamByName("LaLiga")).thenReturn(Optional.of(mockTeam));

        ResponseEntity<TeamResponseDTO> response = teamController.getTeamByName("LaLiga");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Osasuna", response.getBody().getName());
    }

    @Test
    void testGetLeagueByTeam() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        ResponseEntity<LeagueDTO> response = teamController.getLeagueByTeam(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetPlayersByTeam() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        List<PlayerDTO> mockPlayers = List.of(new PlayerDTO());

        when(restService.findPlayersByTeam(mockTeam)).thenReturn(mockPlayers);

        ResponseEntity<List<PlayerDTO>> response = teamController.getPlayersByTeam(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPlayers, response.getBody());
    }

    @Test
    void testGetMatchesByTeam() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        List<MatchDTO> mockMatches = List.of(new MatchDTO());

        when(restService.findMatchesByTeam(mockTeam)).thenReturn(mockMatches);

        ResponseEntity<List<MatchDTO>> response = teamController.getMatchesByTeam(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockMatches, response.getBody());
    }

    @Test
    void testPostTeam() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setLeague(league);

        TeamCreationDTO teamDto = new TeamCreationDTO();
        teamDto.setLeague("LaLiga");

        when(restService.createTeam(any(Team.class), any(League.class))).thenReturn(mockTeam);
        when(restService.findLeagueByName("LaLiga")).thenReturn(Optional.of(league));

        ResponseEntity<TeamResponseDTO> response = teamController.postTeams(null, teamDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());

        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void testDeleteTeam() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        doNothing().when(restService).deleteTeam(mockTeam);

        ResponseEntity<TeamResponseDTO> response = teamController.deleteTeams(1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateTeam() {
        League league = new League();
        league.setId(1);
        league.setName("LaLiga");
        Team mockTeam = new Team();
        mockTeam.setId(1);
        TeamUpdateDTO teamDto = new TeamUpdateDTO();

        mockTeam.setLeague(league);

        when(restService.findTeamById(1)).thenReturn(Optional.of(mockTeam));

        ResponseEntity<TeamResponseDTO> response = teamController.putTeams(1, teamDto);

        assertEquals(200, response.getStatusCode().value());
        verify(restService, times(1)).updateTeam(mockTeam, teamDto, league);
    }
}
