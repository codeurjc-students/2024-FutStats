package com.tfg.futstats.services;

import com.tfg.futstats.FutstatsApplication;
import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.TeamMatch;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.Match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FutstatsApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestServiceTest {

    @Autowired
    private RestService restService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
    }

    // #region LEAGUE CRUD TESTS
    @Test
    @Order(1)
    @Transactional
    public void testSaveLeague() {
        League league = new League("Serie A", "Benito", "Italiana", null, false);

        restService.saveLeague(league);

        Optional<League> returnLeague = restService.findLeagueByName("Serie A");

        assertTrue(returnLeague.isPresent(), "League should be present");

        assertEquals("Serie A", returnLeague.get().getName());
    }

    @Test
    @Order(2)
    @Transactional
    public void testCreateLeague() {
        League league = new League("League1", "Al-Kelaiffy", "Francesa", null, false);

        restService.createLeague(league);

        Optional<League> returnLeague = restService.findLeagueByName("League1");

        assertTrue(returnLeague.isPresent(), "League should be present");

        assertEquals("League1", returnLeague.get().getName());
    }

    @Test
    public void testFindAllLeagues() {
        List<LeagueDTO> leagues = restService.findAllLeagues();

        assertEquals(1, leagues.size());
        assertEquals("LaLiga", leagues.get(0).getName());
    }

    @Test
    public void testFindLeagueById() {
        Optional<League> league = restService.findLeagueById(1);

        assertTrue(league.isPresent(), "League should be present");

        assertEquals("LaLiga", league.get().getName());
    }

    @Test
    public void testFindLeagueByName() {
        Optional<League> league = restService.findLeagueByName("LaLiga");

        assertTrue(league.isPresent(), "League should be present");
        assertEquals(1, league.get().getId());
    }

    @Test
    @Transactional
    public void testUpdateLeague() {
        Optional<League> league = restService.findLeagueByName("LaLiga");

        assertTrue(league.isPresent(), "League should be present");

        League updatedLeague = new League();

        updatedLeague.setPresident("Pellegrini");

        restService.saveLeague(updatedLeague);

        LeagueDTO leagueDto = new LeagueDTO(updatedLeague);

        restService.updateLeague(league.get(), leagueDto);

        Optional<League> returnLeague = restService.findLeagueByName("LaLiga");

        assertTrue(league.isPresent(), "League should be present");

        assertEquals("Pellegrini", returnLeague.get().getPresident());
    }

    @Test
    @Transactional
    public void testDeleteLeague() {
        Optional<League> league = restService.findLeagueByName("LaLiga");

        assertTrue(league.isPresent(), "League should be present");

        restService.deleteLeague(league.get());

        Optional<League> returnLeague = restService.findLeagueByName("LaLiga");

        assertTrue(!returnLeague.isPresent(), "League should not be present");
    }

    @Test
    @Transactional
    public void testFindLeaguesByUser() {
        Optional<League> league = restService.findLeagueByName("LaLiga");

        assertTrue(league.isPresent(), "League should be present");

        league.get().setUser(userService.findUserByName("Admin").get());

        List<LeagueDTO> leagues = restService.findLeaguesByUser(userService.findUserByName("Admin").get());

        assertEquals(1, leagues.size());
        assertEquals("LaLiga", leagues.get(0).getName());
    }

    // #endregion

    // #region TEAM CRUD TESTS
    @Test
    public void testFindTeamById() {
        Optional<Team> team = restService.findTeamById(1);

        assertTrue(team.isPresent(), "Team should be present");

        assertEquals("Real Madrid", team.get().getName());
    }

    @Test
    public void testFindTeamByName() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");

        assertTrue(team.isPresent(), "Team should be present");
        assertEquals(1, team.get().getId());
    }

    @Test
    public void testFindTeamsByLeague() {
        List<TeamResponseDTO> teams = restService.findTeamsByLeague(restService.findLeagueById(1).get());

        assertEquals(2, teams.size());
        assertEquals("FC Barcelona", teams.get(0).getName());
        assertEquals("Real Madrid", teams.get(1).getName());
    }

    @Test
    @Transactional
    public void testFindTeamsByUser() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");

        assertTrue(team.isPresent(), "Team should be present");

        team.get().setUser(userService.findUserByName("Admin").get());

        List<TeamResponseDTO> teams = restService.findTeamsByUser(userService.findUserByName("Admin").get());

        assertEquals(1, teams.size());
        assertEquals("Real Madrid", teams.get(0).getName());
    }

    @Test
    @Order(3)
    @Transactional
    public void testSaveTeam() {
        Team team = new Team(restService.findLeagueByName("LaLiga").get(), "Osasuna", 12, "Spain", "Riojano",
                "Mauricio", "Koldo", "Las Rozas", null, false);

        restService.saveTeam(team);

        Optional<Team> returnTeam = restService.findTeamByName("Osasuna");

        assertTrue(returnTeam.isPresent(), "Team should be present");

        assertEquals("Osasuna", returnTeam.get().getName());
    }

    @Test
    @Order(4)
    @Transactional
    public void testCreateTeam() {
        Team team = new Team(restService.findLeagueByName("LaLiga").get(), "Atletico", 25, "Spain", "Cholo", "Mono",
                "Manzano", "Wanda", null, false);

        League league = restService.findLeagueByName("LaLiga").get();

        restService.createTeam(team, league);

        Optional<Team> returnTeam = restService.findTeamByName("Atletico");

        assertTrue(returnTeam.isPresent(), "Team should be present");

        assertEquals("Atletico", returnTeam.get().getName());
    }

    @Test
    @Transactional
    public void testDeleteTeam() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");

        assertTrue(team.isPresent(), "Team should be present");

        restService.deleteTeam(team.get());

        Optional<Team> returnTeam = restService.findTeamByName("Real Madrid");

        assertTrue(!returnTeam.isPresent(), "Team should not be present");
    }

    @Test
    @Transactional
    public void testUpdateTeam() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");

        assertTrue(team.isPresent(), "Team should be present");

        TeamUpdateDTO teamDto = new TeamUpdateDTO();

        teamDto.setPresident("Pellegrini");

        restService.updateTeam(team.get(), teamDto, restService.findLeagueByName("LaLiga").get());

        Optional<Team> returnTeam = restService.findTeamByName("Real Madrid");

        assertTrue(returnTeam.isPresent(), "Team should be present");

        assertEquals("Pellegrini", returnTeam.get().getPresident());
    }

    @Test
    @Transactional
    public void testUpdateTeamInfo() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");

        assertTrue(team.isPresent(), "Team should be present");

        restService.updateTeamInfo(team.get());

        Optional<Team> returnTeam = restService.findTeamByName("Real Madrid");

        assertTrue(returnTeam.isPresent(), "Team should be present");

        assertEquals("Florentino Pérez", returnTeam.get().getPresident());
    }

    // #endregion

    // #region PLAYER CRUD OPERATIONS

    @Test
    public void testFindPlayerById() {
        Optional<Player> player = restService.findPlayerById(1);

        assertTrue(player.isPresent(), "Player should be present");

        assertEquals("Vinicius Jr.", player.get().getName());
    }

    @Test
    public void testFindPlayerByName() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(player.isPresent(), "Player should be present");

        assertEquals(1, player.get().getId());
    }

    @Test
    public void testFindPlayersByLeague() {
        List<Player> players = restService.findPlayersByLeague(restService.findLeagueById(1).get());

        assertEquals(4, players.size());
        assertEquals("Vinicius Jr.", players.get(0).getName());
        assertEquals("Antonio Rudiguer", players.get(1).getName());
    }

    @Test
    public void testFindPlayersByTeam() {
        List<PlayerDTO> players = restService.findPlayersByTeam(restService.findTeamById(1).get());

        assertEquals(2, players.size());
        assertEquals("Vinicius Jr.", players.get(0).getName());
        assertEquals("Antonio Rudiguer", players.get(1).getName());
    }

    @Test
    @Transactional
    public void testFindPlayersByUser() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");
        assertTrue(player.isPresent(), "Player should be present");

        player.get().setUser(userService.findUserByName("Admin").get());

        List<PlayerDTO> players = restService.findPlayersByUser(userService.findUserByName("Admin").get());

        assertEquals(1, players.size());
        assertEquals("Vinicius Jr.", players.get(0).getName());
    }

    @Test
    @Order(5)
    @Transactional
    public void testSavePlayer() {
        Player player = new Player(restService.findLeagueByName("LaLiga").get(),
                restService.findTeamByName("Real Madrid").get(), "Griezman", 27, "Francesa", "Delantero", null, false);

        restService.savePlayer(player);

        Optional<Player> returnPlayer = restService.findPlayerByName("Griezman");

        assertTrue(returnPlayer.isPresent(), "Player should be present");

        assertEquals("Griezman", returnPlayer.get().getName());
    }

    @Test
    @Order(6)
    @Transactional
    public void testCreatePlayer() {
        Player player = new Player(restService.findLeagueByName("LaLiga").get(),
                restService.findTeamByName("Real Madrid").get(), "NuevoJugador", 20, "Española", "Defensa", null, false);

        League league = restService.findLeagueByName("LaLiga").get();
        Team team = restService.findTeamByName("Real Madrid").get();

        restService.createPlayer(player, league, team);

        Optional<Player> returnPlayer = restService.findPlayerByName("NuevoJugador");

        assertTrue(returnPlayer.isPresent(), "Player should be present");
        assertEquals("NuevoJugador", returnPlayer.get().getName());
    }

    @Test
    @Transactional
    public void testUpdatePlayer() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(player.isPresent(), "Player should be present");

        PlayerDTO playerDto = new PlayerDTO();

        playerDto.setPosition("Centrocampista");

        restService.updatePlayer(player.get(), playerDto, restService.findLeagueByName("LaLiga").get(),
                restService.findTeamByName("Real Madrid").get());

        Optional<Player> returnPlayer = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(returnPlayer.isPresent(), "Player should be present");

        assertEquals("Centrocampista", returnPlayer.get().getPosition());
    }

    @Test
    @Transactional
    public void testDeletePlayer() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(player.isPresent(), "Player should be present");

        restService.deletePlayer(player.get());

        Optional<Player> returnPlayer = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(!returnPlayer.isPresent(), "Player should not be present");
    }

    @Test
    @Transactional
    public void testUpdatePlayerInfo() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(player.isPresent(), "Player should be present");

        restService.updatePlayerInfo(player.get());

        Optional<Player> returnPlayer = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(returnPlayer.isPresent(), "Player should be present");

        assertEquals("Delantero", returnPlayer.get().getPosition());
    }
    // #endregion

    // #region MATCH & TEAMMATCH CRUD OPERATIONS

    @Test
    @Order(7)
    @Transactional
    public void testSaveMatch() {
        Match match = new Match(restService.findLeagueByName("LaLiga").get(),
                restService.findTeamByName("Real Madrid").get(), restService.findTeamByName("FC Barcelona").get(),
                "Real Madrid" + '-' + "FC Barcelona", "Santiago Bernabeu");

        restService.saveMatch(match);

        Optional<Match> returnMatch = restService.findMatchByName("Real Madrid-FC Barcelona");

        assertTrue(returnMatch.isPresent(), "Match should be present");

        assertEquals("Real Madrid-FC Barcelona", returnMatch.get().getName());
    }

    @Test
    @Order(8)
    @Transactional
    public void testCreateMatch() {
        Match match = new Match(restService.findLeagueByName("LaLiga").get(),
                restService.findTeamByName("Real Madrid").get(),
                restService.findTeamByName("FC Barcelona").get(), "Manchester City" + '-' + "Manchester United",
                "Etihiad Stadium");

        League league = restService.findLeagueByName("LaLiga").get();
        Team team = restService.findTeamByName("Real Madrid").get();
        Team team2 = restService.findTeamByName("FC Barcelona").get();

        restService.createMatch(match, league, team, team2);

        Optional<Match> returnMatch = restService.findMatchByName("Real Madrid-FC Barcelona");

        assertTrue(returnMatch.isPresent(), "Match should be present");

        assertEquals("Real Madrid-FC Barcelona", returnMatch.get().getName());
    }

    @Test
    @Order(9)
    @Transactional
    public void testCreateTeamMatch() {
        Optional<Team> team = restService.findTeamByName("Real Madrid");
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");
        TeamMatch teamMatch = new TeamMatch();

        teamMatch.setName("Real Madrid");

        restService.createTeamMatch(teamMatch, match.get(), team.get());

        Optional<TeamMatch> teamReturn = restService.findTeamMatchById(2);

        assertTrue(teamReturn.isPresent(), "TeamMatch should be present");

        assertEquals("Real Madrid", teamReturn.get().getName());
    }

    @Test
    public void testFindMatchById() {
        Optional<Match> match = restService.findMatchById(1);

        assertTrue(match.isPresent(), "Match should be present");

        assertEquals("FC Barcelona-Real Madrid", match.get().getName());
    }

    @Test
    public void testFindMatchByName() {
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");

        assertTrue(match.isPresent(), "Match should be present");

        assertEquals(1, match.get().getId());
    }

    @Test
    public void testFindTeamMatchById() {
        Optional<TeamMatch> match = restService.findTeamMatchById(1);

        assertTrue(match.isPresent(), "TeamMatch should be present");

        assertEquals("FC Barcelona", match.get().getName());
    }

    @Test
    public void testFindAllTeamMatchByTeam() {
        List<TeamMatchDTO> matches = restService.findAllTeamMatchesByTeam(1);

        assertEquals(1, matches.size());
        assertEquals("Real Madrid", matches.get(0).getName());
    }

    @Test
    @Transactional
    public void testUpdateMatch() {
        Optional<Match> match = restService.findMatchById(1);

        assertTrue(match.isPresent(), "Match should be present");

        MatchDTO matchDto = new MatchDTO();

        matchDto.setName("prueba");

        match.get().getTeam1().setStadium("Prueba");

        restService.updateMatch(match.get(), matchDto, restService.findLeagueByName("LaLiga").get(), restService.findTeamByName("Real Madrid").get(), restService.findTeamByName("FC Barcelona").get());

        Optional<Match> returnMatch = restService.findMatchById(2);

        assertTrue(returnMatch.isPresent(), "Match should be present");

        assertEquals("Santiago Bernabeu", returnMatch.get().getPlace());
    }

    @Test
    @Transactional
    public void testUpdateTeamMatch() {
        Optional<Match> match = restService.findMatchById(1);

        Optional<Team> team = restService.findTeamById(1);

        restService.updateTeamMatch(match.get(), team.get());

        Optional<TeamMatch> returnMatch = restService.findTeamMatchById(1);

        assertTrue(returnMatch.isPresent(), "TeamMatch should be present");

        assertEquals(1, returnMatch.get().getPoints());
    }

    @Test
    @Transactional
    public void testDeleteMatch() {
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");

        assertTrue(match.isPresent(), "Match should be present");

        restService.deleteMatch(match.get());

        Optional<Match> returnMatch = restService.findMatchByName("FC Barcelona-Real Madrid");

        assertTrue(returnMatch.isEmpty(), "Match should not be present");
    }

    @Test
    @Transactional
    public void testDeleteTeamMatch() {
        Optional<Match> match = restService.findMatchById(1);

        Optional<Team> team = restService.findTeamById(1);

        Optional<TeamMatch> teamMatch = restService.findTeamMatchById(1);

        restService.deleteTeamMatch(teamMatch.get(), match.get(), team.get());

        Optional<TeamMatch> returnMatch = restService.findTeamMatchById(1);

        assertTrue(!returnMatch.isPresent(), "TeamMatch should not be present");
    }

    @Test
    @Transactional
    public void testUpdateMatchInfo() {
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");

        assertTrue(match.isPresent(), "Match should be present");

        restService.updateMatchInfo(match.get());

        Optional<Match> returnMatch = restService.findMatchByName("FC Barcelona-Real Madrid");

        assertTrue(returnMatch.isPresent(), "Match should be present");

        assertEquals("FC Barcelona-Real Madrid", returnMatch.get().getName());
    }

    // #endregion

    // #region PLAYERMATCH CRUD OPERATIONS

    @Test
    public void testFindAllPlayerMatchesByPlayer() {
        List<PlayerMatchDTO> players = restService.findAllPlayerMatchesByPlayer(1);

        assertEquals(1, players.size());
        assertEquals("FC Barcelona-Real Madrid", players.get(0).getMatchName());
    }

    @Test
    public void testFindAllPlayerMatchesByMatch() {
        List<PlayerMatchDTO> players = restService.findAllPlayerMatchesByMatch(1);

        assertEquals(1, players.size());
        assertEquals("Vinicius Jr.", players.get(0).getName());
    }

    @Test
    public void testFindPlayerMatchById() {
        Optional<PlayerMatch> player = restService.findPlayerMatchById(1);

        assertTrue(player.isPresent(), "Player should be present");

        assertEquals("Vinicius Jr.", player.get().getName());
    }

    @Test
    @Order(10)
    public void testSavePlayerMatch() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");
        PlayerMatch playerMatch = new PlayerMatch();

        playerMatch.setName("Vinicius Jr.");

        restService.savePlayerMatch(playerMatch, match.get(), player.get());

        Optional<PlayerMatch> playerReturn = restService.findPlayerMatchById(1);

        assertTrue(playerReturn.isPresent(), "PlayerMatch should be present");

        assertEquals("Vinicius Jr.", playerReturn.get().getName());
    }

    @Test
    @Order(11)
    @Transactional
    public void testCreatePlayerMatch() {
        Optional<Player> player = restService.findPlayerByName("Lamine Yamal");
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");
        PlayerMatch playerMatch = new PlayerMatch();

        playerMatch.setName("Lamine Yamal");

        restService.createPlayerMatch(playerMatch, match.get(), player.get());

        Optional<PlayerMatch> playerReturn = restService.findPlayerMatchById(2);

        assertTrue(playerReturn.isPresent(), "PlayerMatch should be present");

        assertEquals("Lamine Yamal", playerReturn.get().getName());
    }

    @Test
    @Transactional
    public void testDeletePlayerMatch() {
        Optional<Player> player = restService.findPlayerByName("Vinicius Jr.");
        Optional<Match> match = restService.findMatchByName("FC Barcelona-Real Madrid");
        Optional<PlayerMatch> playerMatch = restService.findPlayerMatchById(1);

        assertTrue(player.isPresent(), "Player should be present");

        restService.deletePlayerMatch(playerMatch.get(), match.get(), player.get());

        Optional<Player> returnPlayer = restService.findPlayerByName("Vinicius Jr.");

        assertTrue(!returnPlayer.isEmpty(), "Player should not be present");
    }

    @Test
    @Transactional
    public void testUpdatePlayerMatch() {
        Optional<PlayerMatch> playerReturn = restService.findPlayerMatchById(1);

        assertTrue(playerReturn.isPresent(), "PlayerMatch should be present");

        PlayerMatchDTO playerDto = new PlayerMatchDTO();

        playerReturn.get().setGoals(1);

        restService.updatePlayerMatch(playerReturn.get(), playerDto,
                restService.findMatchByName("FC Barcelona-Real Madrid").get(),
                restService.findPlayerByName("Vinicius Jr.").get());

        Optional<PlayerMatch> returnPlayer = restService.findPlayerMatchById(1);

        assertTrue(returnPlayer.isPresent(), "Player should be present");

        assertEquals(1, returnPlayer.get().getGoals());
    }

    // #endregion

}
