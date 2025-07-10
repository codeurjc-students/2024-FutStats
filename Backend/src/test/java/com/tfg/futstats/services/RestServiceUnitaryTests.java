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
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        when(leagueRepository.save(league)).thenReturn(league);

        restService.saveLeague(league);

        verify(leagueRepository, times(1)).save(league);
    }

    @Test
    public void testFindLeagueByName() {
        String leagueName = "Serie A";
        League league = new League(leagueName, "Benito", "Italiana", null, false);
        when(leagueRepository.findByNameIgnoreCase(leagueName)).thenReturn(Optional.of(league));

        Optional<League> result = restService.findLeagueByName(leagueName);

        assertTrue(result.isPresent());
        assertEquals(leagueName, result.get().getName());
        verify(leagueRepository, times(1)).findByNameIgnoreCase(leagueName);
    }

    @Test
    public void testFindAllLeagues() {
        League league1 = new League("Serie A", "Benito", "Italiana", null, false);
        League league2 = new League("Premier League", "Smith", "Inglesa", null, false);
        league1.setId(1);
        league2.setId(2);
        when(leagueRepository.findAll()).thenReturn(Arrays.asList(league1, league2));

        List<LeagueDTO> result = restService.findAllLeagues();

        assertEquals(2, result.size());
        assertEquals("Serie A", result.get(0).getName());
        assertEquals("Premier League", result.get(1).getName());
        verify(leagueRepository, times(1)).findAll();
    }

    @Test
    public void testFindLeagueById() {
        long leagueId = 1L;
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        when(leagueRepository.findById(leagueId)).thenReturn(Optional.of(league));

        Optional<League> result = restService.findLeagueById(leagueId);

        assertTrue(result.isPresent());
        assertEquals("Serie A", result.get().getName());
        verify(leagueRepository, times(1)).findById(leagueId);
    }

    @Test
    public void testFindLeaguesByUser() {
        User user = new User();
        user.setName("Admin");

        League league1 = new League("Serie A", "Benito", "Italiana", null, false);
        League league2 = new League("Premier League", "Smith", "Inglesa", null, false);

        league1.setId(1);
        league2.setId(2);

        user.setLeague(league1);
        user.setLeague(league2);

        when(leagueRepository.findAllByUsers(user)).thenReturn(Arrays.asList(league1, league2));

        List<LeagueDTO> result = restService.findLeaguesByUser(user);

        assertEquals(2, result.size());
        assertEquals("Serie A", result.get(0).getName());
        assertEquals("Premier League", result.get(1).getName());
        verify(leagueRepository, times(1)).findAllByUsers(user);
    }

    @Test
    public void testDeleteLeague() {
        League league = new League("Serie A", "Benito", "Italiana", null, false);
        league.setId(1);
        doNothing().when(leagueRepository).delete(league);

        restService.deleteLeague(league);

        verify(leagueRepository, times(1)).delete(league);
    }

    @Test
    public void testUpdateLeague() {
        League existingLeague = new League("Serie A", "Benito", "Italiana", null, false);
        LeagueDTO updatedLeagueDTO = new LeagueDTO();
        updatedLeagueDTO.setPresident("Pellegrini");
        existingLeague.setPresident("Pellegrini");
        when(leagueRepository.save(existingLeague)).thenReturn(existingLeague);

        restService.updateLeague(existingLeague, updatedLeagueDTO);

        assertEquals("Pellegrini", existingLeague.getPresident());
        verify(leagueRepository, times(1)).save(existingLeague);
    }

    // #endregion

    // #region TEAM TESTS

    @Test
    public void testSaveTeam() {
        Team team = new Team();
        team.setName("Osasuna");
        when(teamRepository.save(team)).thenReturn(team);

        restService.saveTeam(team);

        verify(teamRepository, times(1)).save(team);
    }

    @Test
    public void testFindTeamById() {
        long teamId = 1;
        Team team = new Team();
        team.setName("Osasuna");
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        Optional<Team> result = restService.findTeamById(teamId);

        assertTrue(result.isPresent());
        assertEquals("Osasuna", result.get().getName());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    public void testFindTeamByIdNotFound() {
        long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        Optional<Team> result = restService.findTeamById(teamId);

        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    public void testFindTeamByName() {
        String teamName = "Osasuna";
        Team team = new Team();
        team.setName(teamName);
        when(teamRepository.findByNameIgnoreCase(teamName)).thenReturn(Optional.of(team));

        Optional<Team> result = restService.findTeamByName(teamName);

        assertTrue(result.isPresent());
        assertEquals(teamName, result.get().getName());
        verify(teamRepository, times(1)).findByNameIgnoreCase(teamName);
    }

    @Test
    public void testFindTeamByNameNotFound() {
        String teamName = "Nonexistent Team";
        when(teamRepository.findByNameIgnoreCase(teamName)).thenReturn(Optional.empty());

        Optional<Team> result = restService.findTeamByName(teamName);

        assertFalse(result.isPresent());
        verify(teamRepository, times(1)).findByNameIgnoreCase(teamName);
    }

    @Test
    public void testFindTeamsByLeague() {
        League league = new League("La Liga", "Javier Tebas", "Spain", null, false);
        Team team1 = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        Team team2 = new Team(league, "Alaves", 1, "española", "kiko", "", "", "", null, false);
        league.setId(1);
        team1.setId(1);
        team2.setId(2);
        when(teamRepository.findTeamsByLeague(league.getId())).thenReturn(Arrays.asList(team1, team2));

        List<TeamResponseDTO> result = restService.findTeamsByLeague(league);

        assertEquals(2, result.size());
        assertEquals("Osasuna", result.get(0).getName());
        assertEquals("Alaves", result.get(1).getName());
        verify(teamRepository, times(1)).findTeamsByLeague(league.getId());
    }

    @Test
    public void testDeleteTeam() {
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        doNothing().when(teamRepository).delete(team);

        restService.deleteTeam(team);

        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    public void testUpdateTeam() {
        League league = new League("La Liga", "Javier Tebas", "Spain", null, false);
        league.setId(1);
        Team oldTeam = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        oldTeam.setId(1);
        TeamUpdateDTO updateDTO = new TeamUpdateDTO();
        updateDTO.setTrophies(31);

        restService.updateTeam(oldTeam, updateDTO, league);

        assertEquals(31, oldTeam.getTrophies());
        verify(teamRepository, times(1)).save(oldTeam);
    }

    // #endregion

    // #region PLAYER TESTS

    @Test
    public void testCreatePlayer() {
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        Team team = new Team(league, "Osasuna", 1, "española", "kiko", "", "", "", null, false);
        Player player = new Player(league, team, "Alcacer", 35, "Española", "delantero", null, false);

        when(playerRepository.save(player)).thenReturn(player);

        restService.createPlayer(player, league, team);

        verify(playerRepository, times(1)).save(player);
        assertEquals(league, player.getLeague());
        assertEquals(team, player.getTeam());
    }

    @Test
    public void testFindPlayerById() {
        long playerId = 1;
        Player player = new Player();
        player.setName("Alcacer");
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        Optional<Player> result = restService.findPlayerById(playerId);

        assertTrue(result.isPresent());
        assertEquals("Alcacer", result.get().getName());
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    public void testFindPlayerByName() {
        String playerName = "Alcacer";
        Player player = new Player();
        player.setName(playerName);
        when(playerRepository.findByNameIgnoreCase(playerName)).thenReturn(Optional.of(player));

        Optional<Player> result = restService.findPlayerByName(playerName);

        assertTrue(result.isPresent());
        assertEquals(playerName, result.get().getName());
        verify(playerRepository, times(1)).findByNameIgnoreCase(playerName);
    }

    @Test
    public void testFindPlayersByLeague() {
        League league = new League("La Liga", "Spain", "Javier Tebas", null, false);
        Player player1 = new Player();
        player1.setName("Alcacer");
        player1.setLeague(league);
        Player player2 = new Player();
        player2.setName("Paco");
        player2.setLeague(league);

        when(playerRepository.findPlayersByLeague(league)).thenReturn(List.of(player1, player2));

        List<Player> result = restService.findPlayersByLeague(league);

        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findPlayersByLeague(league);
    }

    @Test
    public void testFindPlayersByTeam() {
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

        List<PlayerDTO> result = restService.findPlayersByTeam(team);

        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findPlayersByTeam(team);
    }

    @Test
    public void testDeletePlayer() {
        Player player = new Player();
        player.setId(1);
        player.setName("Alcacer");
        doNothing().when(playerRepository).deleteById(player.getId());

        restService.deletePlayer(player);

        verify(playerRepository, times(1)).deleteById(player.getId());
    }

    @Test
    public void testUpdatePlayer() {
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

        restService.updatePlayer(oldPlayer, playerDTO, league, team);

        assertEquals("Paco", oldPlayer.getName());
        assertEquals(37, oldPlayer.getAge());
        verify(playerRepository, times(1)).save(oldPlayer);
    }

    @Test
    public void testFindPlayersByUser() {
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

        List<PlayerDTO> result = restService.findPlayersByUser(user); // Replace with a valid User instance if needed

        assertEquals(2, result.size());
        assertEquals("Alcacer", result.get(0).getName());
        verify(playerRepository, times(1)).findAllByUsers(user);
    }

    // #endregion

    // #region MATCH TESTS

    @Test
    public void testCreateMatch() {
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

        restService.createMatch(match, league, team1, team2);

        verify(matchRepository, times(1)).save(match);
        assertEquals(league, match.getLeague());
        assertEquals(team1, match.getTeam1());
        assertEquals(team2, match.getTeam2());
    }

    @Test
    public void testFindMatchById() {
        long matchId = 1;
        Match match = new Match();
        match.setName("Match1");
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        Optional<Match> result = restService.findMatchById(matchId);

        assertTrue(result.isPresent());
        assertEquals("Match1", result.get().getName());
        verify(matchRepository, times(1)).findById(matchId);
    }

    @Test
    public void testFindMatchByName() {
        String matchName = "Match1";
        Match match = new Match();
        match.setName(matchName);
        when(matchRepository.findMatchByName(matchName)).thenReturn(Optional.of(match));

        Optional<Match> result = restService.findMatchByName(matchName);

        assertTrue(result.isPresent());
        assertEquals(matchName, result.get().getName());
        verify(matchRepository, times(1)).findMatchByName(matchName);
    }

    @Test
    public void testFindMatchesByLeague() {
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

        List<MatchDTO> result = restService.findMatchesByLeague(league);

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

        List<MatchDTO> result = restService.findMatchesByTeam(team1);

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
        Match match = new Match(league, team1, team2, "Match1", "");
        match.setId(1);

        league.setMatch(match);

        doNothing().when(matchRepository).delete(match);

        restService.deleteMatch(match);

        verify(matchRepository, times(1)).delete(match);
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

        restService.updateMatch(oldMatch, matchDTO, league, team1, team2);

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

        List<MatchDTO> result = restService.findAllMatches();

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

        restService.createPlayerMatch(playerMatch, match, player);

        verify(playerMatchRepository, times(1)).save(playerMatch);
        verify(playerRepository, times(1)).save(player);
        verify(matchRepository, times(1)).save(match);
    }

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

        when(matchRepository.save(any(Match.class))).thenReturn(match);
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(teamRepository.save(any(Team.class))).thenReturn(team1);
        when(teamRepository.save(any(Team.class))).thenReturn(team2);

        restService.deletePlayerMatch(playerMatch, match, player);

        verify(playerMatchRepository, times(1)).delete(playerMatch);
        verify(matchRepository, times(3)).save(match);
        verify(playerRepository, times(3)).save(player);
    }

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

        List<PlayerMatchDTO> result = restService.findAllPlayerMatchesByPlayer(player.getId());

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getGoals());
        assertEquals(1, result.get(1).getGoals());
        verify(playerMatchRepository, times(1)).findAllByPlayer(player.getId());
    }

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

        when(playerMatchRepository.save(any(PlayerMatch.class))).thenReturn(playerMatch);

        restService.deleteData(playerMatch, match, player);

        assertEquals(0, playerMatch.getGoals());
        verify(playerMatchRepository, times(1)).save(playerMatch);
    }

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

        restService.updatePlayerMatch(oldPlayerMatch, playerMatchDTO, match, player);

        assertEquals(3, oldPlayerMatch.getGoals());
        verify(playerMatchRepository, times(1)).save(oldPlayerMatch);
    }

    // #endregion

    // #region TEAMMATCH TESTS

    @Test
    public void testCreateTeamMatch() {
        Match match = new Match();
        match.setName("Match1");
        match.setId(1);
        Team team = new Team();
        team.setName("Osasuna");
        team.setId(1);
        TeamMatch teamMatch = new TeamMatch();
        teamMatch.setId(1);

        when(teamMatchRepository.save(teamMatch)).thenReturn(teamMatch);

        restService.createTeamMatch(teamMatch, match, team);

        verify(teamMatchRepository, times(1)).save(teamMatch);
        assertEquals(match, teamMatch.getMatch());
        assertEquals(team, teamMatch.getTeam());
    }

    @Test
    public void testFindTeamMatchById() {
        long teamMatchId = 1L;
        TeamMatch teamMatch = new TeamMatch();
        teamMatch.setId(teamMatchId);
        teamMatch.setPoints(3);

        when(teamMatchRepository.findById(teamMatchId)).thenReturn(Optional.of(teamMatch));

        Optional<TeamMatch> result = restService.findTeamMatchById(teamMatchId);

        assertTrue(result.isPresent());
        assertEquals(3, result.get().getPoints());
        verify(teamMatchRepository, times(1)).findById(teamMatchId);
    }

    @Test
    public void testFindAllTeamMatchesByTeam() {
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

        List<TeamMatchDTO> result = restService.findAllTeamMatchesByTeam(team1.getId());

        assertEquals(2, result.size());
        assertEquals(0, result.get(0).getPoints());
        assertEquals(0, result.get(1).getPoints());
        verify(teamMatchRepository, times(1)).findByTeamId(team1.getId());
    }

    @Test
    public void testDeleteTeamMatch() {
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

        restService.deleteTeamMatch(teamMatch, match, team);

        verify(teamMatchRepository, times(1)).delete(teamMatch);
    }

    @Test
    public void testUpdateTeamMatch() {
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

        restService.updateTeamMatch(match, team);

        assertEquals(3, existingTeamMatch.getPoints());
        verify(teamMatchRepository, times(1)).save(existingTeamMatch);
    }

    // #endregion
}