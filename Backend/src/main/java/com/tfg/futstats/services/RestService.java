package com.tfg.futstats.services;

// region imports
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.TeamMatch;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.TeamMatchRepository;
import com.tfg.futstats.repositories.TeamRepository;
import com.tfg.futstats.repositories.UserRepository;
import com.tfg.futstats.repositories.MatchRepository;
import com.tfg.futstats.repositories.PlayerMatchRepository;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
//endregion

@Service
public class RestService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerMatchRepository playerMatchRepository;

    @Autowired
    private TeamMatchRepository teamMatchRepository;

    @Autowired
    UserRepository userRepository;

    // region LEAGUE CRUD OPERATIONS
    public List<LeagueDTO> findAllLeagues() {
        List<League> leagues = leagueRepository.findAll();

        return leagues.stream()
                .map(league -> new LeagueDTO(league))
                .toList();
    }

    public Optional<League> findLeagueById(long id) {
        return leagueRepository.findById(id);
    }

    public Optional<League> findLeagueByName(String name) {
        return leagueRepository.findByNameIgnoreCase(name);
    }

    public void saveLeague(League league) {
        leagueRepository.save(league);
    }

    public void createLeague(League league) {
        leagueRepository.save(league);
    }

    public void deleteLeague(League league) {

        if (league.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(league.getUsers());
            for (User user : usersToRemove) {
                user.getLeagues().remove(league);
            }
            league.getUsers().clear();
        }

        List<Player> players = playerRepository.findByLeagueId(league.getId());
        for (Player player : players) {
            league.deletePlayer(player);
            playerRepository.delete(player);
        }

        List<Match> matches = matchRepository.findByLeagueId(league.getId());
        for (Match match : matches) {
            league.deleteMatch(match);
            matchRepository.delete(match);
        }

        List<Team> teams = teamRepository.findByLeagueId(league.getId());
        for (Team team : teams) {
            league.deleteTeam(team);
            teamRepository.delete(team);
        }

        leagueRepository.delete(league);
    }

    public void updateLeague(League league, LeagueDTO leagueDto) {
        // If the admin has not intruduced any data, it will get
        // the data from the unmodified league
        if (leagueDto.getName() != null) {
            league.setName(leagueDto.getName());
        }
        if (leagueDto.getPresident() != null) {
            league.setPresident(leagueDto.getPresident());
        }
        if (leagueDto.getNationality() != null) {
            league.setNationality(leagueDto.getNationality());
        }

        leagueRepository.save(league);
    }

    public List<LeagueDTO> findLeaguesByUser(User u) {
        List<League> leagues = leagueRepository.findAllByUsers(u);

        return leagues.stream()
                .map(league -> new LeagueDTO(league))
                .toList();
    }
    // endregion

    // region TEAM CRUD OPERATIONS
    public List<TeamResponseDTO> findAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(team -> new TeamResponseDTO(team))
                .toList();
    }

    public Optional<Team> findTeamById(long id) {
        return teamRepository.findById(id);
    }

    public Optional<Team> findTeamByName(String name) {
        return teamRepository.findByNameIgnoreCase(name);
    }

    public List<TeamResponseDTO> findTeamsByLeague(League league) {
        List<Team> teams = teamRepository.findTeamsByLeague(league.getId());

        return teams.stream()
                .map(team -> new TeamResponseDTO(team))
                .toList();
    }

    public List<TeamResponseDTO> findTeamsByUser(User u) {
        List<Team> teams = teamRepository.findAllByUsers(u);

        return teams.stream()
                .map(team -> new TeamResponseDTO(team))
                .toList();
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public Team createTeam(Team team, League league) {
        team.setLeague(league);
        league.setTeam(team);
        teamRepository.save(team);
        return team;
    }

    public void deleteTeam(Team team) {
        // Eliminar PlayerMatch relacionado
        List<Player> players = playerRepository.findByTeamId(team.getId());
        for (Player player : players) {
            team.deletePlayer(player);
            playerRepository.delete(player);
        }

        List<Match> matches = matchRepository.findAllByTeam(team.getId());
        for (Match match : matches) {
            matchRepository.delete(match);
        }

        if (team.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(team.getUsers());
            for (User user : usersToRemove) {
                user.getTeams().remove(team);
            }
            team.getUsers().clear();
        }

        List<TeamMatch> teamMatches = teamMatchRepository.findByTeamId(team.getId());
        teamMatchRepository.deleteAll(teamMatches);

        // Eliminar Player
        teamRepository.deleteById(team.getId());
    }

    public void updateTeam(Team oldTeam, TeamUpdateDTO teamDto, League league) {
        // If the admin has not intruduced any data, it will get
        // the data from the unmodified team

        if (teamDto.getName() != null) {
            oldTeam.setName(teamDto.getName());
        }
        if (teamDto.getTrophies() != 0) {
            oldTeam.setTrophies(teamDto.getTrophies());
        }
        if (teamDto.getNationality() != null) {
            oldTeam.setNationality(teamDto.getNationality());
        }
        if (teamDto.getTrainer() != null) {
            oldTeam.setTrainer(teamDto.getTrainer());
        }
        if (teamDto.getSecondTrainer() != null) {
            oldTeam.setSecondTrainer(teamDto.getSecondTrainer());
        }
        if (teamDto.getPresident() != null) {
            oldTeam.setPresident(teamDto.getPresident());
        }
        if (teamDto.getStadium() != null) {
            oldTeam.setStadium(teamDto.getStadium());
        }

        if (oldTeam.getLeague() != league) {
            League oldLeague = oldTeam.getLeague();
            oldLeague.deleteTeam(oldTeam);
            oldTeam.setLeague(league);
            league.setTeam(oldTeam);
        }

        leagueRepository.save(league);

        updateTeamInfo(oldTeam);
    }

    public void updateTeamInfo(Team team) {
        // offensive
        team.setTotalMatches(matchRepository.findMatchesByTeam(team.getId()));
        team.setTotalShoots(matchRepository.findShootsByTeam(team.getId()));
        team.setTotalGoals(matchRepository.findGoalsByTeam(team.getId()));
        team.setPenaltys(matchRepository.findPeanltysByTeam(team.getId()));
        team.setFaultsReceived(matchRepository.findFaultsReceivedByTeam(team.getId()));
        team.setOffsides(matchRepository.findOffsidesByTeam(team.getId()));
        team.setPoints(matchRepository.findWonMatchesByTeam(team.getId()));

        // defensive
        team.setCommitedFaults(matchRepository.findCommitedFaultsByTeam(team.getId()));
        team.setRecovers(matchRepository.findRecoversByTeam(team.getId()));
        team.setDuels(matchRepository.findDuelsByTeam(team.getId()));
        team.setWonDuels(matchRepository.findWonDuelsByTeam(team.getId()));
        team.setYellowCards(matchRepository.findYellowCardsByTeam(team.getId()));
        team.setRedCards(matchRepository.findRedCardsByTeam(team.getId()));

        // creation
        team.setPasses(matchRepository.findPassesByTeam(team.getId()));
        team.setGoodPasses(matchRepository.findGoodPassesByTeam(team.getId()));
        team.setShortPasses(matchRepository.findShortPassesByTeam(team.getId()));
        team.setLongPasses(matchRepository.findLongPassesByTeam(team.getId()));
        team.setAssists(matchRepository.findAssistsByTeam(team.getId()));
        team.setDribles(matchRepository.findDriblesByTeam(team.getId()));
        team.setCenters(matchRepository.findCentersByTeam(team.getId()));
        team.setBallLosses(matchRepository.findBallLossesByTeam(team.getId()));

        // matches
        team.setWonMatches(matchRepository.findWonMatchesByTeam(team.getId()));
        team.setLostMatches(matchRepository.findLostMatchesByTeam(team.getId()));
        team.setDrawMatches(matchRepository.findDrawMatchesByTeam(team.getId()));

        teamRepository.save(team);

    }

    public void saveTeamMatch(TeamMatch teamMatch) {
        teamMatchRepository.save(teamMatch);
    }

    public Optional<TeamMatch> findTeamMatchById(long id) {
        return teamMatchRepository.findById(id);
    }

    public void createTeamMatch(TeamMatch teamMatch, Match match, Team team) {

        teamMatch.setMatch(match);
        teamMatch.setTeam(team);
        teamMatch.setName(team.getName());
        teamMatch.setMatchName(match.getName());
        teamMatch.setPoints(team.getPoints());

        teamMatchRepository.save(teamMatch);

        team.setTeamMatch(teamMatch);
        match.setTeamMatch(teamMatch);
    }

    public List<TeamMatchDTO> findAllTeamMatchesByTeam(long id) {
        List<TeamMatch> teams = teamMatchRepository.findByTeamId(id);

        return teams.stream()
                .map(team -> new TeamMatchDTO(team))
                .toList();
    }

    public void deleteTeamMatch(TeamMatch teamMatch, Match match, Team team) {
        if (team != null) {
            team.deleteTeamMatch(teamMatch);
            teamMatch.setTeam(null);
        }

        if (match != null) {
            match.deleteTeamMatch(teamMatch);
            teamMatch.setMatch(null);
        }

        teamMatchRepository.delete(teamMatch);

        updateTeamInfo(team);
    }

    public void updateTeamMatch(Match match, Team team) {

        TeamMatch teamMatch = teamMatchRepository.findByMatchAndTeamId(match.getId(), team.getId());

        if (teamMatch != null) {
            if (matchRepository.findWinnerByTeam(match.getId(), team.getId()) == team.getId())
                teamMatch.setPoints(3);
            else if (matchRepository.findDrawByTeam(match.getId(), team.getId()) == team.getId())
                teamMatch.setPoints(1);
            else
                teamMatch.setPoints(0);

            teamMatchRepository.save(teamMatch);
        }

    }
    // endregion

    // region PLAYER CRUD OPERATIONS
    public List<PlayerResponseDTO> findAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(player -> new PlayerResponseDTO(player))
                .toList();
    }

    public Optional<Player> findPlayerById(long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findPlayerByName(String name) {
        return playerRepository.findByNameIgnoreCase(name);
    }

    public List<Player> findPlayersByLeague(League league) {
        return playerRepository.findPlayersByLeague(league);
    }

    public List<PlayerDTO> findPlayersByTeam(Team team) {
        List<Player> players = playerRepository.findPlayersByTeam(team);

        return players.stream()
                .map(player -> new PlayerDTO(player))
                .toList();
    }

    public List<PlayerDTO> findPlayersByUser(User u) {
        List<Player> players = playerRepository.findAllByUsers(u);

        return players.stream()
                .map(player -> new PlayerDTO(player))
                .toList();
    }

    public List<PlayerMatchDTO> findAllPlayerMatchesByPlayer(long id) {
        List<PlayerMatch> players = playerMatchRepository.findAllByPlayer(id);

        return players.stream()
                .map(player -> new PlayerMatchDTO(player))
                .toList();
    }

    public List<PlayerMatchDTO> findAllPlayerMatchesByMatch(long id) {
        List<PlayerMatch> players = playerMatchRepository.findAllByMatch(id);

        return players.stream()
                .map(player -> new PlayerMatchDTO(player))
                .toList();
    }

    public Optional<PlayerMatch> findPlayerMatchById(long id) {
        return playerMatchRepository.findById(id);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public Player createPlayer(Player player, League league, Team team) {
        league.setPlayer(player);
        team.setPlayer(player);

        player.setLeague(league);
        player.setTeam(team);

        playerRepository.save(player);

        return player;
    }

    public void createPlayerMatch(PlayerMatch newPlayerMatch, Match match, Player player) {
        newPlayerMatch.setMatch(match);
        newPlayerMatch.setPlayer(player);
        newPlayerMatch.setName(player.getName());
        newPlayerMatch.setMatchName(match.getName());

        player.setPlayerMatch(newPlayerMatch);
        match.setPlayerMatch(newPlayerMatch);

        playerMatchRepository.save(newPlayerMatch);

        updatePlayerInfo(player);
        updateMatchInfo(match);
    }

    public void savePlayerMatch(PlayerMatch playerMatch, Match match, Player player) {
        playerMatch.setMatch(match);
        playerMatch.setPlayer(player);
        playerMatch.setName(player.getName());
        playerMatch.setMatchName(match.getName());

        playerMatchRepository.save(playerMatch);
    }

    public void deletePlayer(Player player) {
        if (player.getTeam() != null) {
            Team team = player.getTeam();
            player.setTeam(null);
            team.deletePlayer(player);
        }

        if (player.getLeague() != null) {
            League league = player.getLeague();
            player.setLeague(null);
            league.deletePlayer(player);
        }

        if (player.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(player.getUsers());
            for (User user : usersToRemove) {
                user.getPlayers().remove(player);
            }
            player.getUsers().clear();
        }

        // Eliminar Player
        playerRepository.deleteById(player.getId());
    }

    public void deletePlayerMatch(PlayerMatch playerMatch, Match match, Player player) {
        deleteData(playerMatch, match, player);

        playerMatch.setMatch(null);
        playerMatch.setPlayer(null);

        match.deletePlayerMatch(playerMatch);
        player.deletePlayerMatch(playerMatch);

        matchRepository.save(match);
        playerRepository.save(player);

        playerMatchRepository.delete(playerMatch);

        updatePlayerInfo(player);
        updateMatchInfo(match);
    }

    public void deleteData(PlayerMatch playerMatch, Match match, Player player) {
        playerMatch.setShoots(0);

        playerMatch.setGoals(0);

        playerMatch.setPenaltys(0);
        playerMatch.setFaultsReceived(0);

        playerMatch.setOffsides(0);

        playerMatch.setFaultsReceived(0);

        playerMatch.setCommitedFaults(0);

        playerMatch.setRecovers(0);

        playerMatch.setFaultsReceived(0);

        playerMatch.setFaultsReceived(0);

        playerMatch.setDuels(0);

        playerMatch.setWonDuels(0);

        playerMatch.setYellowCards(0);

        playerMatch.setRedCards(0);

        playerMatch.setPasses(0);

        playerMatch.setGoodPasses(0);

        playerMatch.setShortPasses(0);

        playerMatch.setLongPasses(0);

        playerMatch.setAssists(0);

        playerMatch.setDribles(0);

        playerMatch.setCenters(0);

        playerMatch.setBallLosses(0);

        playerMatch.setShootsReceived(0);

        playerMatch.setGoalsConceded(0);

        playerMatch.setGoalsConceded(0);

        playerMatch.setSaves(0);

        playerMatch.setGoalsConceded(0);

        playerMatch.setOutBoxSaves(0);

        playerMatch.setInBoxSaves(0);

        playerMatchRepository.save(playerMatch);

        updatePlayerInfo(player);
        updateMatchInfo(match);
    }

    public void updatePlayer(Player oldPlayer, PlayerDTO playerDto, League league, Team team) {

        // If the admin has not intruduced any data, it will get
        // the data from the unmodified league
        if (playerDto.getName() != null) {
            oldPlayer.setName(playerDto.getName());
        }
        if (playerDto.getAge() != 0) {
            oldPlayer.setAge(playerDto.getAge());
        }
        if (playerDto.getNationality() != null) {
            oldPlayer.setNationality(playerDto.getNationality());
        }
        if (playerDto.getPosition() != null) {
            oldPlayer.setPosition(playerDto.getPosition());
        }

        updatePlayerInfo(oldPlayer);

    }

    public void updatePlayerMatch(PlayerMatch oldPlayerMatch, PlayerMatchDTO playerMatchDto, Match match,
            Player player) {

        if (playerMatchDto.getShoots() != 0) {
            oldPlayerMatch.setShoots(playerMatchDto.getShoots());
        }
        if (playerMatchDto.getGoals() != 0) {
            oldPlayerMatch.setGoals(playerMatchDto.getGoals());
        }
        if (playerMatchDto.getPenaltys() != 0) {
            oldPlayerMatch.setPenaltys(playerMatchDto.getPenaltys());
        }
        if (playerMatchDto.getFaultsReceived() != 0) {
            oldPlayerMatch.setFaultsReceived(playerMatchDto.getFaultsReceived());
        }
        if (playerMatchDto.getOffsides() != 0) {
            oldPlayerMatch.setOffsides(playerMatchDto.getOffsides());
        }
        if (playerMatchDto.getFaultsReceived() != 0) {
            oldPlayerMatch.setFaultsReceived(playerMatchDto.getFaultsReceived());
        }
        if (playerMatchDto.getCommitedFaults() != 0) {
            oldPlayerMatch.setCommitedFaults(playerMatchDto.getCommitedFaults());
        }
        if (playerMatchDto.getRecovers() != 0) {
            oldPlayerMatch.setRecovers(playerMatchDto.getRecovers());
        }
        if (playerMatchDto.getFaultsReceived() != 0) {
            oldPlayerMatch.setFaultsReceived(playerMatchDto.getFaultsReceived());
        }
        if (playerMatchDto.getFaultsReceived() != 0) {
            oldPlayerMatch.setFaultsReceived(playerMatchDto.getFaultsReceived());
        }
        if (playerMatchDto.getDuels() != 0) {
            oldPlayerMatch.setDuels(playerMatchDto.getDuels());
        }
        if (playerMatchDto.getWonDuels() != 0) {
            oldPlayerMatch.setWonDuels(playerMatchDto.getWonDuels());
        }
        if (playerMatchDto.getYellowCards() != 0) {
            oldPlayerMatch.setYellowCards(playerMatchDto.getYellowCards());
        }
        if (playerMatchDto.getRedCards() != 0) {
            oldPlayerMatch.setRedCards(playerMatchDto.getRedCards());
        }
        if (playerMatchDto.getPasses() != 0) {
            oldPlayerMatch.setPasses(playerMatchDto.getPasses());
        }
        if (playerMatchDto.getGoodPasses() != 0) {
            oldPlayerMatch.setGoodPasses(playerMatchDto.getGoodPasses());
        }
        if (playerMatchDto.getShortPasses() != 0) {
            oldPlayerMatch.setShortPasses(playerMatchDto.getShortPasses());
        }
        if (playerMatchDto.getLongPasses() != 0) {
            oldPlayerMatch.setLongPasses(playerMatchDto.getLongPasses());
        }
        if (playerMatchDto.getAssists() != 0) {
            oldPlayerMatch.setAssists(playerMatchDto.getAssists());
        }
        if (playerMatchDto.getDribles() != 0) {
            oldPlayerMatch.setDribles(playerMatchDto.getDribles());
        }
        if (playerMatchDto.getCenters() != 0) {
            oldPlayerMatch.setCenters(playerMatchDto.getCenters());
        }
        if (playerMatchDto.getBallLosses() != 0) {
            oldPlayerMatch.setBallLosses(playerMatchDto.getBallLosses());
        }
        if (playerMatchDto.getShootsReceived() != 0) {
            oldPlayerMatch.setShootsReceived(playerMatchDto.getShootsReceived());
        }
        if (playerMatchDto.getGoalsConceded() != 0) {
            oldPlayerMatch.setGoalsConceded(playerMatchDto.getGoalsConceded());
        }
        if (playerMatchDto.getGoalsConceded() != 0) {
            oldPlayerMatch.setGoalsConceded(playerMatchDto.getGoalsConceded());
        }
        if (playerMatchDto.getSaves() != 0) {
            oldPlayerMatch.setSaves(playerMatchDto.getSaves());
        }
        if (playerMatchDto.getGoalsConceded() != 0) {
            oldPlayerMatch.setGoalsConceded(playerMatchDto.getGoalsConceded());
        }
        if (playerMatchDto.getOutBoxSaves() != 0) {
            oldPlayerMatch.setOutBoxSaves(playerMatchDto.getOutBoxSaves());
        }
        if (playerMatchDto.getInBoxSaves() != 0) {
            oldPlayerMatch.setInBoxSaves(playerMatchDto.getInBoxSaves());
        }

        playerMatchRepository.save(oldPlayerMatch);

        updatePlayerInfo(player);
        updateMatchInfo(match);
    }

    // To automaticaly update the players stats
    public void updatePlayerInfo(Player player) {

        player.setTotalMatches(playerMatchRepository.matchesByPlayer(player.getId()));
        player.setTotalShoots(playerMatchRepository.shootsByPlayer(player.getId()));
        player.setTotalGoals(playerMatchRepository.goalsByPlayer(player.getId()));
        player.setPenaltys(playerMatchRepository.penaltysByPlayer(player.getId()));
        player.setFaultsReceived(playerMatchRepository.faultsReceivedByPlayer(player.getId()));
        player.setOffsides(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setCommitedFaults(playerMatchRepository.commitedFaultsByPlayer(player.getId()));
        player.setRecovers(playerMatchRepository.recoversByPlayer(player.getId()));
        player.setDuels(playerMatchRepository.duelsByPlayer(player.getId()));
        player.setWonDuels(playerMatchRepository.wonDuelsByPlayer(player.getId()));
        player.setYellowCards(playerMatchRepository.yellowCardsByPlayer(player.getId()));
        player.setRedCards(playerMatchRepository.redCardsByPlayer(player.getId()));
        player.setPasses(playerMatchRepository.passesByPlayer(player.getId()));
        player.setGoodPasses(playerMatchRepository.goodPassesByPlayer(player.getId()));
        player.setShortPasses(playerMatchRepository.shortPassesByPlayer(player.getId()));
        player.setLongPasses(playerMatchRepository.longPassesByPlayer(player.getId()));
        player.setAssists(playerMatchRepository.assistsByPlayer(player.getId()));
        player.setDribles(playerMatchRepository.driblesByPlayer(player.getId()));
        player.setCenters(playerMatchRepository.centersByPlayer(player.getId()));
        player.setBallLosses(playerMatchRepository.ballLossesByPlayer(player.getId()));

        playerRepository.save(player);
    }
    // endregion

    // region MATCH CRUD OPERATIONS
    public List<MatchDTO> findAllMatches() {
        List<Match> matches = matchRepository.findAll();

        return matches.stream()
                .map(match -> new MatchDTO(match))
                .toList();
    }

    public Optional<Match> findMatchById(long id) {
        return matchRepository.findById(id);
    }

    public Optional<Match> findMatchByName(String name) {
        return matchRepository.findMatchByName(name);
    }

    public List<MatchDTO> findMatchesByLeague(League league) {
        List<Match> matches = matchRepository.findAllByLeague(league.getId());

        return matches.stream()
                .map(match -> new MatchDTO(match))
                .toList();
    }

    public List<MatchDTO> findMatchesByTeam(Team team) {
        List<Match> matches = matchRepository.findAllByTeam(team.getId());

        return matches.stream()
                .map(match -> new MatchDTO(match))
                .toList();
    }

    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    public Match createMatch(Match newMatch, League league, Team team1, Team team2) {

        newMatch.setLeague(league);
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        newMatch.setName(team1.getName() + '-' + team2.getName());
        newMatch.setPlace(team1.getStadium());

        league.setMatch(newMatch);

        matchRepository.save(newMatch);

        matchRepository.flush();

        leagueRepository.save(league);
        teamRepository.save(team1);
        teamRepository.save(team2);

        updateTeamInfo(team1);
        updateTeamInfo(team2);

        TeamMatch teamMatch1 = new TeamMatch();
        TeamMatch teamMatch2 = new TeamMatch();

        createTeamMatch(teamMatch1, newMatch, team1);
        createTeamMatch(teamMatch2, newMatch, team2);

        return newMatch;
    }

    public void deleteMatch(Match match) {
        matchRepository.deleteById(match.getId());
    }

    public void updateMatch(Match oldMatch, MatchDTO matchDto, League league, Team team1, Team team2) {

        if (oldMatch == null || matchDto == null || league == null || team1 == null || team2 == null) {
            throw new IllegalArgumentException("One or more arguments are null");
        }

        oldMatch.setName(team1.getName() + '-' + team2.getName());

        oldMatch.setPlace(team1.getStadium());

        TeamMatch teamMatch1 = teamMatchRepository.findByMatchAndTeamId(oldMatch.getId(), oldMatch.getTeam1().getId());
        TeamMatch teamMatch2 = teamMatchRepository.findByMatchAndTeamId(oldMatch.getId(), oldMatch.getTeam2().getId());

        if (teamMatch1 != null) {
            deleteTeamMatch(teamMatch1, oldMatch, oldMatch.getTeam1());
        }

        if (teamMatch2 != null) {
            deleteTeamMatch(teamMatch2, oldMatch, oldMatch.getTeam2());
        }

        teamMatchRepository.flush();

        if (teamMatchRepository.findByMatchAndTeamId(oldMatch.getId(), team1.getId()) == null) {
            TeamMatch teamMatch3 = new TeamMatch();
            createTeamMatch(teamMatch3, oldMatch, team1);
        }

        if (teamMatchRepository.findByMatchAndTeamId(oldMatch.getId(), team2.getId()) == null) {
            TeamMatch teamMatch4 = new TeamMatch();
            createTeamMatch(teamMatch4, oldMatch, team2);
        }

        teamMatchRepository.flush();

        oldMatch.getLeague().deleteMatch(oldMatch);
        Team team1Old = oldMatch.getTeam1();
        Team team2Old = oldMatch.getTeam2();

        oldMatch.setLeague(league);
        oldMatch.setTeam1(team1);
        oldMatch.setTeam2(team2);

        league.setMatch(oldMatch);

        matchRepository.save(oldMatch);

        updateTeamInfo(team1Old);
        updateTeamInfo(team2Old);

        updateMatchInfo(oldMatch);
    }

    // To automatically update the match stats
    public void updateMatchInfo(Match match) {

        // team 1 update stats
        match.setShoots1(playerMatchRepository.shootsByTeam(match.getId(), match.getTeam1().getId()));
        match.setScores1(playerMatchRepository.goalsByTeam(match.getId(), match.getTeam1().getId()));
        match.setPenaltys1(playerMatchRepository.penaltysByTeam(match.getId(), match.getTeam1().getId()));
        match.setFaultsReceived1(playerMatchRepository.faultsReceivedByTeam(match.getId(), match.getTeam1().getId()));
        match.setOffsides1(playerMatchRepository.offsidesByTeam(match.getId(), match.getTeam1().getId()));
        match.setCommitedFaults1(playerMatchRepository.commitedFaultsByTeam(match.getId(), match.getTeam1().getId()));
        match.setRecovers1(playerMatchRepository.recoversByTeam(match.getId(), match.getTeam1().getId()));
        match.setDuels1(playerMatchRepository.driblesByTeam(match.getId(), match.getTeam1().getId()));
        match.setWonDuels1(playerMatchRepository.wonDuelsByTeam(match.getId(), match.getTeam1().getId()));
        match.setYellowCards1(playerMatchRepository.yellowCardsByTeam(match.getId(), match.getTeam1().getId()));
        match.setRedCards1(playerMatchRepository.redCardsByTeam(match.getId(), match.getTeam1().getId()));
        match.setPasses1(playerMatchRepository.passesByTeam(match.getId(), match.getTeam1().getId()));
        match.setGoodPasses1(playerMatchRepository.goodPassesByTeam(match.getId(), match.getTeam1().getId()));
        match.setShortPasses1(playerMatchRepository.shortPassesByTeam(match.getId(), match.getTeam1().getId()));
        match.setLongPasses1(playerMatchRepository.longPassesByTeam(match.getId(), match.getTeam1().getId()));
        match.setAssists1(playerMatchRepository.assistsByTeam(match.getId(), match.getTeam1().getId()));
        match.setDribles1(playerMatchRepository.driblesByTeam(match.getId(), match.getTeam1().getId()));
        match.setCenters1(playerMatchRepository.centersByTeam(match.getId(), match.getTeam1().getId()));
        match.setBallLosses1(playerMatchRepository.ballLossesByTeam(match.getId(), match.getTeam1().getId()));

        // team 2 update stats
        match.setShoots2(playerMatchRepository.shootsByTeam(match.getId(), match.getTeam2().getId()));
        match.setScores2(playerMatchRepository.goalsByTeam(match.getId(), match.getTeam2().getId()));
        match.setPenaltys2(playerMatchRepository.penaltysByTeam(match.getId(), match.getTeam2().getId()));
        match.setFaultsReceived2(playerMatchRepository.faultsReceivedByTeam(match.getId(), match.getTeam2().getId()));
        match.setOffsides2(playerMatchRepository.offsidesByTeam(match.getId(), match.getTeam2().getId()));
        match.setCommitedFaults2(playerMatchRepository.commitedFaultsByTeam(match.getId(), match.getTeam2().getId()));
        match.setRecovers2(playerMatchRepository.recoversByTeam(match.getId(), match.getTeam2().getId()));
        match.setDuels2(playerMatchRepository.driblesByTeam(match.getId(), match.getTeam2().getId()));
        match.setWonDuels2(playerMatchRepository.wonDuelsByTeam(match.getId(), match.getTeam2().getId()));
        match.setYellowCards2(playerMatchRepository.yellowCardsByTeam(match.getId(), match.getTeam2().getId()));
        match.setRedCards2(playerMatchRepository.redCardsByTeam(match.getId(), match.getTeam2().getId()));
        match.setPasses2(playerMatchRepository.passesByTeam(match.getId(), match.getTeam2().getId()));
        match.setGoodPasses2(playerMatchRepository.goodPassesByTeam(match.getId(), match.getTeam2().getId()));
        match.setShortPasses2(playerMatchRepository.shortPassesByTeam(match.getId(), match.getTeam2().getId()));
        match.setLongPasses2(playerMatchRepository.longPassesByTeam(match.getId(), match.getTeam2().getId()));
        match.setAssists2(playerMatchRepository.assistsByTeam(match.getId(), match.getTeam2().getId()));
        match.setDribles2(playerMatchRepository.driblesByTeam(match.getId(), match.getTeam2().getId()));
        match.setCenters2(playerMatchRepository.centersByTeam(match.getId(), match.getTeam2().getId()));
        match.setBallLosses2(playerMatchRepository.ballLossesByTeam(match.getId(), match.getTeam2().getId()));

        matchRepository.save(match);

        updateTeamInfo(match.getTeam1());
        updateTeamInfo(match.getTeam2());

        updateTeamMatch(match, match.getTeam1());
        updateTeamMatch(match, match.getTeam2());
    }
    // endregion
}
