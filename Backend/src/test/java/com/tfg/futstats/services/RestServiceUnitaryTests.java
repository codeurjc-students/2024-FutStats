package com.tfg.futstats.services;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.User;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.TeamMatch;
import com.tfg.futstats.repositories.TeamRepository;
import com.tfg.futstats.repositories.UserRepository;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.PlayerMatchRepository;
import com.tfg.futstats.repositories.TeamMatchRepository;
import com.tfg.futstats.repositories.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestServiceUnitaryTests {

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerMatchRepository playerMatchRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamMatchRepository teamMatchRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestService restService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // #region LEAGUE TESTS

    @Test
    public void testSaveLeague() {
        // Arrange
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        when(leagueRepository.save(league)).thenReturn(league);

        // Act
        restService.saveLeague(league);

        // Assert
        verify(leagueRepository, times(1)).save(league);
    }

    // @Test
    // public void testSaveLeagueWithNullName() {
    // // Arrange
    // League league = new League(null, "Benito", "Italiana", null, false);

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.saveLeague(league);
    // });
    // assertEquals("League name cannot be null", exception.getMessage());
    // }

    @Test
    public void testFindLeagueByName() {
        // Arrange
        String leagueName = "Serie A";
        League league = new League(leagueName, "Benito", "Italiana", null, false);
        when(leagueRepository.findByNameIgnoreCase(leagueName)).thenReturn(Optional.of(league));

        // Act
        Optional<League> result = restService.findLeagueByName(leagueName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(leagueName, result.get().getName());
        verify(leagueRepository, times(1)).findByNameIgnoreCase(leagueName);
    }

    // @Test
    // public void testFindLeagueByNameNotFound() {
    // // Arrange
    // String leagueName = "Nonexistent League";
    // when(leagueRepository.findByNameIgnoreCase(leagueName)).thenReturn(Optional.empty());

    // // Act
    // Optional<League> result = restService.findLeagueByName(leagueName);

    // // Assert
    // assertFalse(result.isPresent());
    // verify(leagueRepository, times(1)).findByNameIgnoreCase(leagueName);
    // }

    @Test
    public void testFindAllLeagues() {
        // Arrange
        League league1 = new League("Serie A", "Benito", "Italiana", null, false);
        League league2 = new League("Premier League", "Smith", "Inglesa", null, false);
        league1.setId(1);
        league2.setId(2);
        when(leagueRepository.findAll()).thenReturn(Arrays.asList(league1, league2));

        // Act
        List<LeagueDTO> result = restService.findAllLeagues();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Serie A", result.get(0).getName());
        assertEquals("Premier League", result.get(1).getName());
        verify(leagueRepository, times(1)).findAll();
    }

    @Test
    public void testFindLeagueById() {
        // Arrange
        long leagueId = 1L;
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        when(leagueRepository.findById(leagueId)).thenReturn(Optional.of(league));

        // Act
        Optional<League> result = restService.findLeagueById(leagueId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Serie A", result.get().getName());
        verify(leagueRepository, times(1)).findById(leagueId);
    }

    // @Test
    // public void testFindLeagueByIdNotFound() {
    // // Arrange
    // long leagueId = 1L;
    // when(leagueRepository.findById(leagueId)).thenReturn(Optional.empty());

    // // Act
    // Optional<League> result = restService.findLeagueById(leagueId);

    // // Assert
    // assertFalse(result.isPresent());
    // verify(leagueRepository, times(1)).findById(leagueId);
    // }

    @Test
    public void testFindLeaguesByUser() {
        // Arrange
        User user = new User();
        user.setName("Admin");

        League league1 = new League("Serie A", "Benito", "Italiana", null, false);
        League league2 = new League("Premier League", "Smith", "Inglesa", null, false);

        league1.setId(1);
        league2.setId(2);

        user.setLeague(league1);
        user.setLeague(league2);

        when(leagueRepository.findAllByUsers(user)).thenReturn(Arrays.asList(league1, league2));

        // Act
        List<LeagueDTO> result = restService.findLeaguesByUser(user);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Serie A", result.get(0).getName());
        assertEquals("Premier League", result.get(1).getName());
        verify(leagueRepository, times(1)).findAllByUsers(user);
    }

    // @Test
    // public void testFindLeaguesByUserWithNoLeagues() {
    // // Arrange
    // User user = new User();
    // user.setName("EmptyUser");

    // when(leagueRepository.findAllByUsers(user)).thenReturn(Arrays.asList());

    // // Act
    // List<LeagueDTO> result = restService.findLeaguesByUser(user);

    // // Assert
    // assertTrue(result.isEmpty());
    // verify(leagueRepository, times(1)).findAllByUsers(user);
    // }

    @Test
    public void testDeleteLeague() {
        // Arrange
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        league.setId(1);
        doNothing().when(leagueRepository).delete(league);

        // Act
        restService.deleteLeague(league);

        // Assert
        verify(leagueRepository, times(1)).delete(league);
    }

    // @Test
    // public void testDeleteLeagueNotExisting() {
    // // Arrange
    // League league = new League("Nonexistent League", "Unknown", "Unknown", null,
    // false);
    // league.setId(1);
    // doThrow(new IllegalArgumentException("League not
    // found")).when(leagueRepository).delete(league);

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.deleteLeague(league);
    // });
    // assertEquals("League not found", exception.getMessage());
    // }

    @Test
    public void testUpdateLeague() {
        // Arrange
        League existingLeague = new League("Serie A", "Benito", "Italiana", null, false);
        LeagueDTO updatedLeagueDTO = new LeagueDTO();
        updatedLeagueDTO.setPresident("Pellegrini");
        existingLeague.setPresident("Pellegrini");
        when(leagueRepository.save(existingLeague)).thenReturn(existingLeague);

        // Act
        restService.updateLeague(existingLeague, updatedLeagueDTO);

        // Assert
        assertEquals("Pellegrini", existingLeague.getPresident());
        verify(leagueRepository, times(1)).save(existingLeague);
    }

    // @Test
    // public void testUpdateNonexistentLeague() {
    // // Arrange
    // League nonexistentLeague = new League("Nonexistent League", "Unknown",
    // "Unknown", null, false);
    // LeagueDTO updateDTO = new LeagueDTO();
    // updateDTO.setPresident("New President");

    // when(leagueRepository.findById(nonexistentLeague.getId())).thenReturn(Optional.empty());

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.updateLeague(nonexistentLeague, updateDTO);
    // });
    // assertEquals("League not found", exception.getMessage());
    // }

    // #endregion

    // #region TEAM TESTS

    @Test
    public void testSaveTeam() {
        // Arrange
        Team team = new Team();
        team.setName("Osasuna");
        when(teamRepository.save(team)).thenReturn(team);

        // Act
        restService.saveTeam(team);

        // Assert
        verify(teamRepository, times(1)).save(team);
    }

    // @Test
    // public void testSaveTeamWithNullName() {
    // // Arrange
    // Team team = new Team();

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.saveTeam(team);
    // });
    // assertEquals("Team name cannot be null", exception.getMessage());
    // }

    @Test
    public void testFindTeamById() {
        // Arrange
        long teamId = 1;
        Team team = new Team();
        team.setName("Osasuna");
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // Act
        Optional<Team> result = restService.findTeamById(teamId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Osasuna", result.get().getName());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    public void testFindTeamByIdNotFound() {
        // Arrange
        long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // Act
        Optional<Team> result = restService.findTeamById(teamId);

        // Assert
        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    public void testFindTeamByName() {
        // Arrange
        String teamName = "Osasuna";
        Team team = new Team();
        team.setName(teamName);
        when(teamRepository.findByNameIgnoreCase(teamName)).thenReturn(Optional.of(team));

        // Act
        Optional<Team> result = restService.findTeamByName(teamName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(teamName, result.get().getName());
        verify(teamRepository, times(1)).findByNameIgnoreCase(teamName);
    }

    @Test
    public void testFindTeamByNameNotFound() {
        // Arrange
        String teamName = "Nonexistent Team";
        when(teamRepository.findByNameIgnoreCase(teamName)).thenReturn(Optional.empty());

        // Act
        Optional<Team> result = restService.findTeamByName(teamName);

        // Assert
        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findByNameIgnoreCase(teamName);
    }

    @Test
    public void testFindTeamsByLeague() {
        // Arrange
        League league = new League("La Liga", "Javier Tebas", "Spain", null, false);
        Team team1 = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        Team team2 = new Team(league, "Alaves", 1, "española", "kiko", "", "", "", null, false);
        league.setId(1);
        team1.setId(1);
        team2.setId(2);
        when(teamRepository.findTeamsByLeague(league.getId())).thenReturn(Arrays.asList(team1, team2));

        // Act
        List<TeamResponseDTO> result = restService.findTeamsByLeague(league);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Osasuna", result.get(0).getName());
        assertEquals("Alaves", result.get(1).getName());
        verify(teamRepository, times(1)).findTeamsByLeague(league.getId());
    }

    // @Test
    // public void testFindTeamsByLeagueNoTeamsFound() {
    // // Arrange
    // League league = new League("La Liga", "Javier Tebas", "Spain", null, false);
    // when(teamRepository.findTeamsByLeague(league.getId())).thenReturn(Arrays.asList());

    // // Act
    // List<TeamResponseDTO> result = restService.findTeamsByLeague(league);

    // // Assert
    // assertTrue(result.isEmpty());
    // verify(teamRepository, times(1)).findTeamsByLeague(league.getId());
    // }

    @Test
    public void testDeleteTeam() {
        // Arrange
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        doNothing().when(teamRepository).deleteById(team.getId());

        // Act
        restService.deleteTeam(team);

        // Assert
        verify(teamRepository, times(1)).deleteById(team.getId());
    }

    // @Test
    // public void testDeleteNonexistentTeam() {
    // // Arrange
    // Team team = new Team();
    // team.setName("Nonexistent Team");
    // doThrow(new IllegalArgumentException("Team not
    // found")).when(teamRepository).deleteById(team.getId());

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.deleteTeam(team);
    // });
    // assertEquals("Team not found", exception.getMessage());
    // }

    @Test
    public void testUpdateTeam() {
        // Arrange
        League league = new League("La Liga", "Javier Tebas", "Spain", null, false);
        league.setId(1);
        Team oldTeam = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        oldTeam.setId(1);
        TeamUpdateDTO updateDTO = new TeamUpdateDTO();
        updateDTO.setTrophies(31);

        // Act
        restService.updateTeam(oldTeam, updateDTO, league);

        // Assert
        assertEquals(31, oldTeam.getTrophies());
        verify(teamRepository, times(1)).save(oldTeam);
    }

    // @Test
    // public void testUpdateNonexistentTeam() {
    // // Arrange
    // Team nonexistentTeam = new Team();
    // nonexistentTeam.setName("Nonexistent Team");
    // TeamUpdateDTO updateDTO = new TeamUpdateDTO();
    // updateDTO.setTrophies(10);

    // when(teamRepository.findById(nonexistentTeam.getId())).thenReturn(Optional.empty());

    // // Act & Assert
    // Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    // restService.updateTeam(nonexistentTeam, updateDTO, null);
    // });
    // assertEquals("Team not found", exception.getMessage());
    // }
    // #endregion

    // #region PLAYER TESTS

    @Test
    public void testCreatePlayer() {
        // Arrange
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        Team team = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        Player player = new Player(league, team, "Alcacer", 35, "Española", "delantero", null, false);

        when(playerRepository.save(player)).thenReturn(player);

        // Act
        restService.createPlayer(player, league, team);

        // Assert
        verify(playerRepository, times(1)).save(player);
        assertEquals(league, player.getLeague());
        assertEquals(team, player.getTeam());
    }

    @Test
    public void testFindPlayerById() {
        // Arrange
        long playerId = 1;
        Player player = new Player();
        player.setName("Alcacer");
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Act
        Optional<Player> result = restService.findPlayerById(playerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Alcacer", result.get().getName());
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    public void testFindPlayerByName() {
        // Arrange
        String playerName = "Alcacer";
        Player player = new Player();
        player.setName(playerName);
        when(playerRepository.findByNameIgnoreCase(playerName)).thenReturn(Optional.of(player));

        // Act
        Optional<Player> result = restService.findPlayerByName(playerName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(playerName, result.get().getName());
        verify(playerRepository, times(1)).findByNameIgnoreCase(playerName);
    }

    @Test
    public void testFindPlayersByLeague() {
        // Arrange
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        Player player1 = new Player();
        player1.setName("Alcacer");
        player1.setLeague(league);
        Player player2 = new Player();
        player2.setName("Paco");
        player2.setLeague(league);

        when(playerRepository.findPlayersByLeague(league)).thenReturn(List.of(player1, player2));

        // Act
        List<Player> result = restService.findPlayersByLeague(league);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findPlayersByLeague(league);
    }

    @Test
    public void testFindPlayersByTeam() {
        // Arrange
        League league = new League();
        league.setName("LaLiga");
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        Player player1 = new Player();
        player1.setId(1);
        ;
        player1.setName("Alcacer");
        player1.setTeam(team);
        player1.setLeague(league);
        Player player2 = new Player();
        player2.setId(2);
        player2.setName("Paco");
        player2.setTeam(team);
        player2.setLeague(league);

        team.setPlayer(player1);
        team.setPlayer(player2);
        league.setPlayer(player1);
        league.setPlayer(player2);

        when(playerRepository.findPlayersByTeam(team)).thenReturn(List.of(player1, player2));

        // Act
        List<PlayerDTO> result = restService.findPlayersByTeam(team);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findPlayersByTeam(team);
    }

    @Test
    public void testDeletePlayer() {
        // Arrange
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        doNothing().when(playerRepository).deleteById(player.getId());

        // Act
        restService.deletePlayer(player);

        // Assert
        verify(playerRepository, times(1)).deleteById(player.getId());
    }

    @Test
    public void testUpdatePlayer() {
        // Arrange
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        team.setName("Osasuna");
        Player oldPlayer = new Player();
        oldPlayer.setId(1);
        oldPlayer.setLeague(league);
        oldPlayer.setTeam(team);
        oldPlayer.setName("Alcacer");
        oldPlayer.setAge(36);
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName("Paco");
        playerDTO.setAge(37);

        // Act
        restService.updatePlayer(oldPlayer, playerDTO, league, team);

        // Assert
        assertEquals("Paco", oldPlayer.getName());
        assertEquals(37, oldPlayer.getAge());
        verify(playerRepository, times(1)).save(oldPlayer);
    }

    @Test
    public void testFindPlayersByUser() {
        // Arrange
        long userId = 1;
        User user = new User();
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team = new Team();
        team.setId(1);
        team.setLeague(league);
        team.setName("Osasuna");
        user.setId(userId);
        Player player1 = new Player();
        player1.setId(1);
        player1.setLeague(league);
        player1.setTeam(team);
        player1.setName("Alcacer");
        Player player2 = new Player();
        player2.setId(2);
        player2.setLeague(league);
        player2.setTeam(team);
        player2.setName("Paco");

        user.setPlayer(player1);
        user.setPlayer(player2);
        user.setLeague(league);
        user.setTeam(team);

        when(playerRepository.findAllByUsers(user)).thenReturn(List.of(player1, player2));

        // Act
        List<PlayerDTO> result = restService.findPlayersByUser(user); // Replace with a valid User instance if needed

        // Assert
        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findAllByUsers(user);
    }

    // #endregion

    // #region MATCH TESTS

    @Test
    public void testCreateMatch() {
        // Arrange
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Osasuna");
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Alaves");
        team2.setLeague(league);
        Match match = new Match();
        match.setId(1);
        match.setLeague(league);
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setName("Match1");

        when(matchRepository.save(match)).thenReturn(match);

        // Act
        restService.createMatch(match, league, team1, team2);

        // Assert
        verify(matchRepository, times(1)).save(match);
        assertEquals(league, match.getLeague());
        assertEquals(team1, match.getTeam1());
        assertEquals(team2, match.getTeam2());
    }

    @Test
    public void testFindMatchById() {
        // Arrange
        long matchId = 1;
        Match match = new Match();
        match.setName("Match1");
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        // Act
        Optional<Match> result = restService.findMatchById(matchId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Match1", result.get().getName());
        verify(matchRepository, times(1)).findById(matchId);
    }

    @Test
    public void testFindMatchByName() {
        // Arrange
        String matchName = "Match1";
        Match match = new Match();
        match.setName(matchName);
        when(matchRepository.findMatchByName(matchName)).thenReturn(Optional.of(match));

        // Act
        Optional<Match> result = restService.findMatchByName(matchName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(matchName, result.get().getName());
        verify(matchRepository, times(1)).findMatchByName(matchName);
    }

    @Test
    public void testFindMatchesByLeague() {
        // Arrange
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Osasuna");
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Alaves");
        team2.setLeague(league);
        String matchName1 = "Match1";
        Match match1 = new Match();
        match1.setId(1);
        match1.setName(matchName1);
        match1.setTeam1(team1);
        match1.setTeam2(team2);
        String matchName2 = "Match1";
        Match match2 = new Match();
        match2.setId(2);
        match2.setName(matchName2);
        match2.setTeam1(team1);
        match2.setTeam2(team2);

        league.setMatch(match1);
        league.setMatch(match2);

        when(matchRepository.findAllByLeague(league.getId())).thenReturn(List.of(match1, match2));

        // Act
        List<MatchDTO> result = restService.findMatchesByLeague(league);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Match1", result.get(0).getName());
        verify(matchRepository, times(1)).findAllByLeague(league.getId());
    }

    @Test
    public void testFindMatchesByTeam() {
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Osasuna");
        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Alaves");
        Match match1 = new Match();
        match1.setId(1);
        match1.setName("Match1");
        match1.setTeam1(team1);
        match1.setTeam2(team2);
        Match match2 = new Match();
        match2.setId(2);
        match2.setName("Match2");
        match2.setTeam1(team1);
        match2.setTeam2(team2);

        league.setMatch(match1);
        league.setMatch(match2);

        when(matchRepository.findAllByTeam(team1.getId())).thenReturn(List.of(match1, match2));

        // Act
        List<MatchDTO> result = restService.findMatchesByTeam(team1);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Match1", result.get(0).getName());
        verify(matchRepository, times(1)).findAllByTeam(team1.getId());
    }

    @Test
    public void testDeleteMatch() {
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Osasuna");
        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Alaves");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);

        league.setMatch(match);

        doNothing().when(matchRepository).deleteById(match.getId());

        // Act
        restService.deleteMatch(match);

        // Assert
        verify(matchRepository, times(1)).deleteById(match.getId());
    }

    @Test
    public void testUpdateMatch() {

        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        team1.setName("Real Madrid");
        team1.setStadium("Santiago Bernabéu");
        Team team2 = new Team();
        team2.setId(2);
        team2.setLeague(league);
        team2.setName("Osasuna");
        Match oldMatch = new Match(league, team1, team2, "Match1", "");
        oldMatch.setId(1);
        MatchDTO matchDTO = new MatchDTO();

        // Act
        restService.updateMatch(oldMatch, matchDTO, league, team1, team2);

        // Assert
        assertEquals("Santiago Bernabéu", oldMatch.getPlace());
        verify(matchRepository, times(2)).save(oldMatch);
    }

    @Test
    public void testFindAllMatches() {
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        league.setId(1);
        Team team1 = new Team();
        team1.setId(1);
        team1.setLeague(league);
        team1.setName("Osasuna");
        Team team2 = new Team();
        team2.setId(2);
        team2.setLeague(league);
        team2.setName("Osasuna");
        Match match1 = new Match();
        match1.setId(1);
        match1.setName("Match1");
        match1.setLeague(league);
        match1.setTeam1(team1);
        match1.setTeam2(team2);
        Match match2 = new Match();
        match2.setId(2);
        match2.setName("Match2");
        match2.setLeague(league);
        match2.setTeam1(team1);
        match2.setTeam2(team2);

        league.setMatch(match1);
        league.setMatch(match2);

        when(matchRepository.findAll()).thenReturn(List.of(match1, match2));

        // Act
        List<MatchDTO> result = restService.findAllMatches();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Match1", result.get(0).getName());
        assertEquals("Match2", result.get(1).getName());
        verify(matchRepository, times(1)).findAll();
    }

    // #endregion

    // #region PLAYERMATCH TESTS

    @Test
    public void testCreatePlayerMatch() {
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);
        PlayerMatch playerMatch = new PlayerMatch();
        player.setId(1);
        playerMatch.setPlayer(player);
        playerMatch.setMatch(match);
        playerMatch.setGoals(2);
        playerMatch.setAssists(1);

        player.setPlayerMatch(playerMatch);
        match.setPlayerMatch(playerMatch);

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        when(playerMatchRepository.save(playerMatch)).thenReturn(playerMatch);

        // Act
        restService.createPlayerMatch(playerMatch, match, player);

        // Assert
        verify(playerMatchRepository, times(1)).save(playerMatch);
        verify(playerRepository, times(1)).save(player);
        verify(matchRepository, times(1)).save(match);
    }

    // @Test
    // public void testCreatePlayerMatchWithNonExistentPlayer() {
    // // Arrange
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch playerMatch = new PlayerMatch();
    // playerMatch.setMatch(match);
    // playerMatch.setGoals(2);

    // when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
    // when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));

    // // Act & Assert
    // assertThrows(RuntimeException.class, () ->
    // restService.createPlayerMatch(playerMatch, match, null));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class));
    // }

    // @Test
    // public void testCreatePlayerMatchWithNonExistentMatch() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // PlayerMatch playerMatch = new PlayerMatch();
    // playerMatch.setPlayer(player);
    // playerMatch.setGoals(2);

    // when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
    // when(matchRepository.findById(anyLong())).thenReturn(Optional.empty());

    // // Act & Assert
    // assertThrows(RuntimeException.class, () ->
    // restService.createPlayerMatch(playerMatch, null, player));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class));
    // }

    @Test
    public void testFindPlayerMatchById() {
        // Arrange
        long playerMatchId = 1;
        PlayerMatch playerMatch = new PlayerMatch();
        playerMatch.setId(playerMatchId);
        playerMatch.setGoals(3);

        when(playerMatchRepository.findById(playerMatchId)).thenReturn(Optional.of(playerMatch));

        // Act
        Optional<PlayerMatch> result = restService.findPlayerMatchById(playerMatchId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getGoals());
        verify(playerMatchRepository, times(1)).findById(playerMatchId);
    }

    // @Test
    // public void testFindPlayerMatchByInvalidId() {
    // // Arrange
    // long invalidPlayerMatchId = 999;

    // when(playerMatchRepository.findById(invalidPlayerMatchId)).thenReturn(Optional.empty());

    // // Act
    // Optional<PlayerMatch> result =
    // restService.findPlayerMatchById(invalidPlayerMatchId);

    // // Assert
    // assertTrue(result.isEmpty());
    // verify(playerMatchRepository, times(1)).findById(invalidPlayerMatchId);
    // }

    @Test
    public void testDeletePlayerMatch() {
        // Arrange
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);
        PlayerMatch playerMatch = new PlayerMatch();
        playerMatch.setId(1);
        playerMatch.setPlayer(player);
        playerMatch.setMatch(match);
        playerMatch.setGoals(2);
        playerMatch.setAssists(1);

        // Configura el comportamiento de los repositorios
        when(matchRepository.save(any(Match.class))).thenReturn(match);
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(teamRepository.save(any(Team.class))).thenReturn(team1); // team1 o team2 dependiendo de las necesidades
        when(teamRepository.save(any(Team.class))).thenReturn(team2);

        // Act
        restService.deletePlayerMatch(playerMatch, match, player);

        // Assert
        verify(playerMatchRepository, times(1)).delete(playerMatch); // Verifica que se eliminó PlayerMatch
        verify(matchRepository, times(3)).save(match); // Verifica que el match se actualizó
        verify(playerRepository, times(3)).save(player); // Verifica que el jugador se actualizó
    }

    // @Test
    // public void testDeletePlayerMatchWithInvalidPlayer() {
    // // Arrange
    // PlayerMatch playerMatch = new PlayerMatch();
    // Match match = new Match();
    // match.setName("Match1");

    // when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

    // // Act & Assert
    // assertThrows(RuntimeException.class, () ->
    // restService.deletePlayerMatch(playerMatch, match, null));

    // verify(playerMatchRepository, never()).delete(playerMatch);
    // }

    // @Test
    // public void testDeletePlayerMatchWithInvalidMatch() {
    // // Arrange
    // PlayerMatch playerMatch = new PlayerMatch();
    // Player player = new Player();
    // player.setName("Alcacer");

    // when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

    // // Act & Assert
    // assertThrows(RuntimeException.class, () ->
    // restService.deletePlayerMatch(playerMatch, null, player));

    // verify(playerMatchRepository, never()).delete(playerMatch);
    // }

    @Test
    public void testFindAllPlayerMatchesByPlayer() {
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);
        PlayerMatch playerMatch1 = new PlayerMatch();
        playerMatch1.setId(1);
        playerMatch1.setPlayer(player);
        playerMatch1.setMatch(match);
        playerMatch1.setGoals(2);
        playerMatch1.setAssists(1);
        PlayerMatch playerMatch2 = new PlayerMatch();
        playerMatch2.setId(2);
        playerMatch2.setPlayer(player);
        playerMatch2.setMatch(match);
        playerMatch2.setGoals(1);
        playerMatch2.setAssists(1);

        player.setPlayerMatch(playerMatch1);
        match.setPlayerMatch(playerMatch1);
        player.setPlayerMatch(playerMatch2);
        match.setPlayerMatch(playerMatch2);

        when(playerMatchRepository.findAllByPlayer(player.getId())).thenReturn(List.of(playerMatch1, playerMatch2));

        // Act
        List<PlayerMatchDTO> result = restService.findAllPlayerMatchesByPlayer(player.getId());

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getGoals());
        assertEquals(1, result.get(1).getGoals());
        verify(playerMatchRepository, times(1)).findAllByPlayer(player.getId());
    }

    // @Test
    // public void testFindAllPlayerMatchesByNonExistentPlayer() {
    // // Arrange
    // long invalidPlayerId = 999L;

    // when(playerMatchRepository.findAllByPlayer(invalidPlayerId)).thenReturn(List.of());

    // // Act
    // List<PlayerMatchDTO> result =
    // restService.findAllPlayerMatchesByPlayer(invalidPlayerId);

    // // Assert
    // assertTrue(result.isEmpty());
    // verify(playerMatchRepository, times(1)).findAllByPlayer(invalidPlayerId);
    // }

    // @Test
    // public void testFindAllPlayerMatchesByMatch() {
    // // Arrange
    // long matchId = 1L;
    // PlayerMatch playerMatch1 = new PlayerMatch();
    // playerMatch1.setGoals(1);
    // PlayerMatch playerMatch2 = new PlayerMatch();
    // playerMatch2.setGoals(2);

    // when(playerMatchRepository.findAllByMatch(matchId)).thenReturn(List.of(playerMatch1,
    // playerMatch2));

    // // Act
    // List<PlayerMatchDTO> result =
    // restService.findAllPlayerMatchesByMatch(matchId);

    // // Assert
    // assertEquals(2, result.size());
    // assertEquals(1, result.get(0).getGoals());
    // assertEquals(2, result.get(1).getGoals());
    // verify(playerMatchRepository, times(1)).findAllByMatch(matchId);
    // }

    // @Test
    // public void testFindAllPlayerMatchesByNonExistentMatch() {
    // // Arrange
    // long invalidMatchId = 999L;

    // when(playerMatchRepository.findAllByMatch(invalidMatchId)).thenReturn(List.of());

    // // Act
    // List<PlayerMatchDTO> result =
    // restService.findAllPlayerMatchesByMatch(invalidMatchId);

    // // Assert
    // assertTrue(result.isEmpty());
    // verify(playerMatchRepository, times(1)).findAllByMatch(invalidMatchId);
    // }

    @Test
    public void testDeleteData() {
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);
        PlayerMatch playerMatch = new PlayerMatch();
        playerMatch.setId(1);
        playerMatch.setPlayer(player);
        playerMatch.setMatch(match);
        playerMatch.setGoals(2);
        playerMatch.setAssists(1);

        // Configura el comportamiento del repositorio
        when(playerMatchRepository.save(any(PlayerMatch.class))).thenReturn(playerMatch);

        // Act
        restService.deleteData(playerMatch, match, player);

        // Assert
        assertEquals(0, playerMatch.getGoals());
        verify(playerMatchRepository, times(1)).save(playerMatch);
    }

    // @Test
    // public void testDeleteDataWithNullPlayerMatch() {
    // // Arrange
    // Match match = new Match();
    // match.setName("Match1");
    // Player player = new Player();
    // player.setName("Alcacer");

    // // Act & Assert
    // assertThrows(NullPointerException.class, () -> restService.deleteData(null,
    // match, player));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class));
    // }

    // @Test
    // public void testDeletePlayerMatchWithNullMatch() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // PlayerMatch playerMatch = new PlayerMatch();
    // playerMatch.setPlayer(player);
    // playerMatch.setMatch(null); // Match es null en este caso

    // doNothing().when(playerMatchRepository).delete(playerMatch);

    // // Act
    // restService.deletePlayerMatch(playerMatch, null, player);

    // // Assert
    // verify(playerMatchRepository, times(1)).delete(playerMatch);
    // verify(playerRepository, times(1)).save(player);
    // verify(matchRepository, never()).save(any(Match.class)); // No debería
    // intentar guardar un partido null
    // }

    // @Test
    // public void testDeletePlayerMatchWithNullPlayer() {
    // // Arrange
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch playerMatch = new PlayerMatch();
    // playerMatch.setPlayer(null);
    // playerMatch.setMatch(match); // Match es null en este caso

    // doNothing().when(playerMatchRepository).delete(playerMatch);

    // // Act
    // restService.deletePlayerMatch(playerMatch, match, null);

    // // Assert
    // verify(playerMatchRepository, times(1)).delete(playerMatch);
    // verify(matchRepository, times(1)).save(match);
    // verify(matchRepository, never()).save(any(Match.class)); // No debería
    // intentar guardar un partido null
    // }

    @Test
    public void testUpdatePlayerMatch() {
        Team team1 = new Team();
        team1.setId(1);
        Team team2 = new Team();
        team2.setId(2);
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        Match match = new Match();
        match.setId(1);
        match.setName("Match1");
        match.setTeam1(team1);
        match.setTeam2(team2);
        PlayerMatch oldPlayerMatch = new PlayerMatch();
        oldPlayerMatch.setId(1);
        oldPlayerMatch.setPlayer(player);
        oldPlayerMatch.setMatch(match);
        oldPlayerMatch.setGoals(2);
        oldPlayerMatch.setAssists(1);
        PlayerMatchDTO playerMatchDTO = new PlayerMatchDTO();
        playerMatchDTO.setGoals(3);

        player.setPlayerMatch(oldPlayerMatch);
        match.setPlayerMatch(oldPlayerMatch);

        // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

        // Act
        restService.updatePlayerMatch(oldPlayerMatch, playerMatchDTO, match, player);

        // Assert
        assertEquals(3, oldPlayerMatch.getGoals());
        verify(playerMatchRepository, times(1)).save(oldPlayerMatch);
    }

    // @Test
    // public void testUpdatePlayerMatchWithInvalidMatch() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch oldPlayerMatch = new PlayerMatch();
    // oldPlayerMatch.setGoals(1);
    // PlayerMatchDTO playerMatchDTO = new PlayerMatchDTO();
    // playerMatchDTO.setGoals(3);

    // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

    // // Act & Assert
    // assertThrows(RuntimeException.class,
    // () -> restService.updatePlayerMatch(oldPlayerMatch, oldPlayerMatch,
    // playerMatchDTO, null, player) // Match
    // // es
    // // null
    // );

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class)); // No se
    // debería guardar
    // }

    // @Test
    // public void testUpdatePlayerMatchWithInvalidPlayer() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch oldPlayerMatch = new PlayerMatch();
    // oldPlayerMatch.setGoals(1);
    // PlayerMatchDTO playerMatchDTO = new PlayerMatchDTO();
    // playerMatchDTO.setGoals(3);

    // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

    // // Act & Assert
    // assertThrows(RuntimeException.class,
    // () -> restService.updatePlayerMatch(oldPlayerMatch, oldPlayerMatch,
    // playerMatchDTO, match, null) // Player
    // // es
    // // null
    // );

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class)); // No se
    // debería guardar
    // }

    // @Test
    // public void testUpdatePlayerMatchWithInvalidNewPlayerMatch() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch oldPlayerMatch = new PlayerMatch();
    // oldPlayerMatch.setGoals(1);
    // PlayerMatchDTO playerMatchDTO = new PlayerMatchDTO();
    // playerMatchDTO.setGoals(3);

    // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

    // // Act & Assert
    // assertThrows(IllegalArgumentException.class,
    // () -> restService.updatePlayerMatch(oldPlayerMatch, null, playerMatchDTO,
    // match, player));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class)); // No se
    // debería guardar
    // }

    // @Test
    // public void testUpdatePlayerMatchWithInvalidOldPlayerMatch() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch newPlayerMatch = new PlayerMatch();
    // newPlayerMatch.setGoals(1);
    // PlayerMatchDTO playerMatchDTO = new PlayerMatchDTO();
    // playerMatchDTO.setGoals(3);
    // PlayerMatch oldPlayerMatch = null;

    // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

    // // Act & Assert
    // assertThrows(IllegalArgumentException.class,
    // () -> restService.updatePlayerMatch(oldPlayerMatch, newPlayerMatch,
    // playerMatchDTO, match, player));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class)); // No se
    // debería guardar
    // }

    // @Test
    // public void testUpdatePlayerMatchWithInvalidDTO() {
    // // Arrange
    // Player player = new Player();
    // player.setName("Alcacer");
    // Match match = new Match();
    // match.setName("Match1");
    // PlayerMatch oldPlayerMatch = new PlayerMatch();
    // oldPlayerMatch.setGoals(1);

    // when(playerMatchRepository.findById(oldPlayerMatch.getId())).thenReturn(Optional.of(oldPlayerMatch));

    // // Act & Assert
    // assertThrows(IllegalArgumentException.class,
    // () -> restService.updatePlayerMatch(oldPlayerMatch, oldPlayerMatch, null,
    // match, player));

    // verify(playerMatchRepository, never()).save(any(PlayerMatch.class)); // No se
    // debería guardar
    // }

    // #endregion

    // #region TEAMMATCH TESTS

    @Test
    public void testCreateTeamMatch() {
        // Arrange
        Match match = new Match();
        match.setName("Match1");
        Team team = new Team();
        team.setName("Osasuna");
        TeamMatch teamMatch = new TeamMatch();
        teamMatch.setId(1);

        when(teamMatchRepository.save(teamMatch)).thenReturn(teamMatch);

        // Act
        restService.createTeamMatch(teamMatch, match, team);

        // Assert
        verify(teamMatchRepository, times(1)).save(teamMatch);
        assertEquals(match, teamMatch.getMatch());
        assertEquals(team, teamMatch.getTeam());
    }

    @Test
    public void testFindTeamMatchById() {
        // Arrange
        long teamMatchId = 1L;
        TeamMatch teamMatch = new TeamMatch();
        teamMatch.setId(teamMatchId);
        teamMatch.setPoints(3);

        when(teamMatchRepository.findById(teamMatchId)).thenReturn(Optional.of(teamMatch));

        // Act
        Optional<TeamMatch> result = restService.findTeamMatchById(teamMatchId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getPoints());
        verify(teamMatchRepository, times(1)).findById(teamMatchId);
    }

    @Test
    public void testFindAllTeamMatchesByTeam() {
        // Arrange
        Match match = new Match();
        match.setName("Match1");
        match.setId(1);
        Team team1 = new Team();
        team1.setName("Osasuna");
        team1.setId(1);
        Team team2 = new Team();
        team2.setName("Alaves");
        team2.setId(2);
        TeamMatch teamMatch1 = new TeamMatch();
        teamMatch1.setId(1);
        match.setTeamMatch(teamMatch1);
        team1.setTeamMatch(teamMatch1);
        team2.setTeamMatch(teamMatch1);
        teamMatch1.setMatch(match);
        teamMatch1.setTeam(team1);
        TeamMatch teamMatch2 = new TeamMatch();
        teamMatch2.setId(2);
        match.setTeamMatch(teamMatch2);
        team1.setTeamMatch(teamMatch2);
        team2.setTeamMatch(teamMatch2);
        teamMatch2.setMatch(match);
        teamMatch2.setTeam(team1);

        match.setTeam1(team1);
        match.setTeam2(team2);

        when(teamMatchRepository.findByTeamId(team1.getId())).thenReturn(List.of(teamMatch1, teamMatch2));

        // Act
        List<TeamMatchDTO> result = restService.findAllTeamMatchesByTeam(team1.getId());

        // Assert
        assertEquals(2, result.size());
        assertEquals(0, result.get(0).getPoints());
        assertEquals(0, result.get(1).getPoints());
        verify(teamMatchRepository, times(1)).findByTeamId(team1.getId());
    }

    @Test
    public void testDeleteTeamMatch() {
        // Arrange
        Match match = new Match();
        match.setName("Match1");
        match.setId(1);
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        TeamMatch teamMatch = new TeamMatch();
        match.setTeamMatch(teamMatch);
        team.setTeamMatch(teamMatch);
        teamMatch.setMatch(match);
        teamMatch.setTeam(team);

        doNothing().when(teamMatchRepository).delete(teamMatch);

        // Act
        restService.deleteTeamMatch(teamMatch, match, team);

        // Assert
        verify(teamMatchRepository, times(1)).delete(teamMatch);
    }

    @Test
    public void testUpdateTeamMatch() {
        // Arrange
        Match match = new Match();
        match.setName("Match 1");
        match.setId(1);
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        TeamMatch existingTeamMatch = new TeamMatch();
        existingTeamMatch.setPoints(3);

        match.setTeam1(team);

        when(teamMatchRepository.findByMatchAndTeamId(match.getId(), team.getId())).thenReturn(existingTeamMatch);
        when(matchRepository.findWinnerByTeam(match.getId(), team.getId())).thenReturn(1);

        // Act
        restService.updateTeamMatch(match, team);

        // Assert
        assertEquals(3, existingTeamMatch.getPoints()); // Assuming points updated to 3 for a win
        verify(teamMatchRepository, times(1)).save(existingTeamMatch);
    }

    // #endregion
}