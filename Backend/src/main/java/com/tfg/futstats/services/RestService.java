package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.TeamRepository;
import com.tfg.futstats.repositories.UserRepository;
import com.tfg.futstats.repositories.MatchRepository;
import com.tfg.futstats.repositories.PlayerMatchRepository;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

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
    UserRepository userRepository;

    // --------------------------------------- LEAGUE CRUD OPERATIONS
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

    public void saveLeague(League league){
        leagueRepository.save(league);
    }

    public void createLeague(League league) {
        leagueRepository.save(league);
    }

    public void deleteLeague(League league) {
        // Eliminar las relaciones de los equipos
        if (league.getTeams() != null) {
            List<Team> teamsToRemove = new ArrayList<>(league.getTeams());
            for (Team team : teamsToRemove) {
                deleteTeam(team);
            }
        }

        // Eliminar las relaciones de los partidos
        if (league.getMatches() != null) {
            List<Match> matchesToRemove = new ArrayList<>(league.getMatches());
            for (Match match : matchesToRemove) {
                deleteMatch(match);
            }
        }

        // Eliminar las relaciones de los jugadores
        if (league.getPlayers() != null) {
            List<Player> playersToRemove = new ArrayList<>(league.getPlayers());
            for (Player player : playersToRemove) {
                deletePlayer(player);
            }
        }

        // Eliminar las relaciones con los usuarios
        if (league.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(league.getUsers());
            for (User user : usersToRemove) {
                user.getLeagues().remove(league);
            }
            league.getUsers().clear();
        }

        // Finalmente, eliminar la liga
        leagueRepository.delete(league);
    }

    public void updateLeague(League oldLeague, League newLeague, LeagueDTO leagueDto) {
        // If the admin has not intruduced any data, it will get
        // the data from the unmodified league
        if (leagueDto.getName() != null) {
            newLeague.setName(oldLeague.getName());
        }
        if (leagueDto.getPresident() != null) {
            newLeague.setPresident(oldLeague.getPresident());
        }
        if (leagueDto.getNationality() != null) {
            newLeague.setNationality(oldLeague.getNationality());
        }

        newLeague.setTeams(oldLeague.getTeams());
        newLeague.setMatches(oldLeague.getMatches());
        newLeague.setPlayers(oldLeague.getPlayers());
        newLeague.setUsers(oldLeague.getUsers());

        leagueRepository.save(newLeague);
    }

    public List<LeagueDTO> findLeaguesByUser(User u) {
        List<League> leagues = leagueRepository.findAllByUsers(u);

        return leagues.stream()
                .map(league -> new LeagueDTO(league))
                .toList();
    }

    // --------------------------------------- TEAM CRUD OPERATIONS
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
        List<Team> teams = teamRepository.findTeamsByLeague(league);

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

    public void saveTeam(Team team){
        teamRepository.save(team);
    }

    public void createTeam(Team team, League league) {
        team.setLeague(league);
        league.setTeam(team);
        teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        if (team.getLeague() != null) {
            League league = team.getLeague();
            league.getTeams().remove(team);
            team.setLeague(null);
        }

        if (team.getPlayers() != null) {
            List<Player> playersToRemove = new ArrayList<>(team.getPlayers());
            for (Player player : playersToRemove) {
                deletePlayer(player);
            }
        }

        if (team.getMatches() != null) {
            List<Match> matchesToRemove = new ArrayList<>(team.getMatches());
            for (Match match : matchesToRemove) {
                deleteMatch(match);
            }
        }

        if (team.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(team.getUsers());
            for (User user : usersToRemove) {
                user.getTeams().remove(team);
            }
            team.getUsers().clear();
        }

        teamRepository.delete(team);
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

    // --------------------------------------- PLAYER CRUD OPERATIONS
    public List<PlayerDTO> findAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(player -> new PlayerDTO(player))
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

    public void savePlayer(Player player){
        playerRepository.save(player);
    }

    public void createPlayer(Player player, League league, Team team) {
        league.setPlayer(player);
        team.setPlayer(player);

        player.setLeague(league);
        player.setTeam(team);

        playerRepository.save(player);
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

    public void deletePlayer(Player player) {
        if (player.getLeague() != null) {
            League league = player.getLeague();
            player.setLeague(null);
            league.deletePlayer(player);
            leagueRepository.save(league);
        }

        // Eliminar relaciones con los jugadores
        if (player.getPlayerMatches() != null) {
            List<PlayerMatch> playerMatchToRemove = new ArrayList<>(player.getPlayerMatches());
            for (PlayerMatch playerMatch : playerMatchToRemove) {
                Match match = matchRepository.findById(playerMatch.getId()).get();
                Player player2 = playerMatch.getPlayer();

                match.deletePlayerMatch(playerMatch);
                player2.deletePlayerMatch(playerMatch);

                matchRepository.save(match);
                playerRepository.save(player2);

                playerMatchRepository.deleteById(playerMatch.getId());
            }
        }

        if (player.getTeam() != null) {
            Team team = player.getTeam();
            player.setTeam(null);
            team.deletePlayer(player);
            teamRepository.save(team);
        }

        if (player.getUsers() != null) {
            List<User> usersToRemove = new ArrayList<>(player.getUsers());
            for (User user : usersToRemove) {
                user.getPlayers().remove(player);
            }
            player.getUsers().clear();
        }

        playerRepository.delete(player);
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

    public void updatePlayerMatch(PlayerMatch oldPlayerMatch, PlayerMatch newPlayerMatch, PlayerMatchDTO playerMatchDto,
            Match match, Player player) {
        newPlayerMatch.setId(oldPlayerMatch.getId());
        newPlayerMatch.setName(oldPlayerMatch.getName());
        newPlayerMatch.setMatchName(oldPlayerMatch.getMatchName());

        if (playerMatchDto.getShoots() == 0) {
            newPlayerMatch.setShoots(oldPlayerMatch.getShoots());
        }
        if (playerMatchDto.getGoals() == 0) {
            newPlayerMatch.setGoals(oldPlayerMatch.getGoals());
        }
        if (playerMatchDto.getPenaltys() == 0) {
            newPlayerMatch.setPenaltys(oldPlayerMatch.getPenaltys());
        }
        if (playerMatchDto.getFaultsReceived() == 0) {
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if (playerMatchDto.getOffsides() == 0) {
            newPlayerMatch.setOffsides(oldPlayerMatch.getOffsides());
        }
        if (playerMatchDto.getFaultsReceived() == 0) {
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if (playerMatchDto.getCommitedFaults() == 0) {
            newPlayerMatch.setCommitedFaults(oldPlayerMatch.getCommitedFaults());
        }
        if (playerMatchDto.getRecovers() == 0) {
            newPlayerMatch.setRecovers(oldPlayerMatch.getRecovers());
        }
        if (playerMatchDto.getFaultsReceived() == 0) {
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if (playerMatchDto.getFaultsReceived() == 0) {
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if (playerMatchDto.getDuels() == 0) {
            newPlayerMatch.setDuels(oldPlayerMatch.getDuels());
        }
        if (playerMatchDto.getWonDuels() == 0) {
            newPlayerMatch.setWonDuels(oldPlayerMatch.getWonDuels());
        }
        if (playerMatchDto.getYellowCards() == 0) {
            newPlayerMatch.setYellowCards(oldPlayerMatch.getYellowCards());
        }
        if (playerMatchDto.getRedCards() == 0) {
            newPlayerMatch.setRedCards(oldPlayerMatch.getRedCards());
        }
        if (playerMatchDto.getPasses() == 0) {
            newPlayerMatch.setPasses(oldPlayerMatch.getPasses());
        }
        if (playerMatchDto.getGoodPasses() == 0) {
            newPlayerMatch.setGoodPasses(oldPlayerMatch.getGoodPasses());
        }
        if (playerMatchDto.getShortPasses() == 0) {
            newPlayerMatch.setShortPasses(oldPlayerMatch.getShortPasses());
        }
        if (playerMatchDto.getLongPasses() == 0) {
            newPlayerMatch.setLongPasses(oldPlayerMatch.getLongPasses());
        }
        if (playerMatchDto.getAssists() == 0) {
            newPlayerMatch.setAssists(oldPlayerMatch.getAssists());
        }
        if (playerMatchDto.getDribles() == 0) {
            newPlayerMatch.setDribles(oldPlayerMatch.getDribles());
        }
        if (playerMatchDto.getCenters() == 0) {
            newPlayerMatch.setCenters(oldPlayerMatch.getCenters());
        }
        if (playerMatchDto.getBallLosses() == 0) {
            newPlayerMatch.setBallLosses(oldPlayerMatch.getBallLosses());
        }
        if (playerMatchDto.getShootsReceived() == 0) {
            newPlayerMatch.setShootsReceived(oldPlayerMatch.getShootsReceived());
        }
        if (playerMatchDto.getGoalsConceded() == 0) {
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if (playerMatchDto.getGoalsConceded() == 0) {
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if (playerMatchDto.getSaves() == 0) {
            newPlayerMatch.setSaves(oldPlayerMatch.getSaves());
        }
        if (playerMatchDto.getGoalsConceded() == 0) {
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if (playerMatchDto.getOutBoxSaves() == 0) {
            newPlayerMatch.setOutBoxSaves(oldPlayerMatch.getOutBoxSaves());
        }
        if (playerMatchDto.getInBoxSaves() == 0) {
            newPlayerMatch.setInBoxSaves(oldPlayerMatch.getInBoxSaves());
        }

        newPlayerMatch.setMatch(oldPlayerMatch.getMatch());
        newPlayerMatch.setPlayer(oldPlayerMatch.getPlayer());

        playerMatchRepository.save(newPlayerMatch);

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

    public void testingPlayerMatchAttributes(Player oldPlayer, Player newPlayer) {

    }

    // --------------------------------------- MATCH CRUD OPERATIONS
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

    public void saveMatch(Match match){
        matchRepository.save(match);
    }

    public void createMatch(Match newMatch, League league, Team team1, Team team2) {
        newMatch.setLeague(league);
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        newMatch.setName(team1.getName() + '-' + team2.getName());
        newMatch.setPlace(team1.getStadium());

        league.setMatch(newMatch);
        team1.setMatch(newMatch);
        team2.setMatch(newMatch);

        matchRepository.save(newMatch);

        leagueRepository.save(league);
        teamRepository.save(team1);
        teamRepository.save(team2);
    }

    public void deleteMatch(Match match) {
        // Eliminar relación con la liga
        if (match.getLeague() != null) {
            League league = match.getLeague();
            match.setLeague(null);
            league.deleteMatch(match);
            leagueRepository.save(league);
        }

        // Eliminar relaciones con los jugadores
        if (match.getPlayerMatches() != null) {
            List<PlayerMatch> playerMatchToRemove = new ArrayList<>(match.getPlayerMatches());
            for (PlayerMatch playerMatch : playerMatchToRemove) {
                deletePlayerMatch(playerMatch, match, playerMatch.getPlayer());
            }
        }

        if (match.getTeam1() != null) {
            Team team1 = match.getTeam1();
            match.setTeam1(null);
            team1.deleteMatch(match);
            teamRepository.save(team1);
        }

        if (match.getTeam2() != null) {
            Team team2 = match.getTeam2();
            match.setTeam2(null);
            team2.deleteMatch(match);
            teamRepository.save(team2);
        }

        matchRepository.delete(match);
    }

    public void updateMatch(Match oldMatch, MatchDTO matchDto, League league, Team team1, Team team2) {

        oldMatch.setName(team1.getName() + '-' + team2.getName());

        if (matchDto.getPlace() != null) {
            oldMatch.setPlace(matchDto.getPlace());
        }

        oldMatch.setLeague(league);
        oldMatch.setTeam1(team1);
        oldMatch.setTeam2(team2);

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
    }
}
