package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.TeamRepository;
import com.tfg.futstats.repositories.MatchRepository;
import com.tfg.futstats.repositories.PlayerMatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

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

    // --------------------------------------- LEAGUE CRUD OPERATIONS
    // ---------------------------------------

    public Page<League> findAllLeagues(Pageable pageable) {
        return leagueRepository.findAll(pageable);
    }

    public Optional<League> findLeagueById(long id) {
        return leagueRepository.findById(id);
    }

    public Optional<League> findLeagueByName(String name) {
        return leagueRepository.findByNameIgnoreCase(name);
    }

    public void createLeague(League league) {
        leagueRepository.save(league);
    }

    public void deleteLeague(League league) {
        leagueRepository.delete(league);
    }

    public void updateLeague(long id, League modLeague) {
        League oldLeague = findLeagueById(id).get();

        modLeague.setTeams(oldLeague.getTeams());
        modLeague.setMatches(oldLeague.getMatches());
        modLeague.setPlayers(oldLeague.getPlayers());

        leagueRepository.save(modLeague);
    }

    public Page<League> findLeaguesByUser(User u, Pageable pageable) {
        return leagueRepository.findAllByUser(u, pageable);
    }

    // --------------------------------------- TEAM CRUD OPERATIONS
    // ---------------------------------------

    public Page<Team> findAllTeams(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    public Optional<Team> findTeamById(long id) {
        return teamRepository.findById(id);
    }

    public Page<Team> findTeamsByLeague(League league, Pageable pageable) {
        return teamRepository.findTeamsByLeague(league, pageable);
    }

    public Optional<Team> findTeamByName(String name) {
        return teamRepository.findByNameIgnoreCase(name);
    }

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    public void updateTeam(long id, Team modTeam) {
        Team oldTeam = findTeamById(id).get();

        modTeam.setLeague(oldTeam.getLeague());
        modTeam.setMatches(oldTeam.getMatches());
        modTeam.setPlayers(oldTeam.getPlayers());

        teamRepository.save(modTeam);
    }

    public Page<Team> findTeamsByUser(User u, Pageable pageable) {
        return teamRepository.findAllByUser(u, pageable);
    }

    public void updateTeamInfo(Team team) {

        //offensive
        team.setTotalMatches(matchRepository.findMatchesByTeam(team.getName()));
        team.setTotalShoots(matchRepository.findShootsByTeam(team.getName()));
        team.setTotalGoals(matchRepository.findGoalsByTeam(team.getName()));
        team.setPenaltys(matchRepository.findPeanltysByTeam(team.getName()));
        team.setFaultsReceived(matchRepository.findFaultsReceivedByTeam(team.getName()));
        team.setOffsides(matchRepository.findOffsidesByTeam(team.getName()));

        // defensive
        team.setCommitedFaults(matchRepository.findCommitedFaultsByTeam(team.getName()));
        team.setRecovers(matchRepository.findRecoversByTeam(team.getName()));
        team.setDuels(matchRepository.findDuelsByTeam(team.getName()));
        team.setWonDuels(matchRepository.findWonDuelsByTeam(team.getName()));
        team.setYellowCards(matchRepository.findYellowCardsByTeam(team.getName()));
        team.setRedCards(matchRepository.findRedCardsByTeam(team.getName()));

        // creation
        team.setPossesion(matchRepository.findAveragePossessionByTeam(team.getName()));
        team.setPasses(matchRepository.findPassesByTeam(team.getName()));
        team.setGoodPasses(matchRepository.findGoodPassesByTeam(team.getName()));
        team.setShortPasses(matchRepository.findShortPassesByTeam(team.getName()));
        team.setLongPasses(matchRepository.findLongPassesByTeam(team.getName()));
        team.setAssists(matchRepository.findAssistsByTeam(team.getName()));
        team.setDribles(matchRepository.findDriblesByTeam(team.getName()));
        team.setCenters(matchRepository.findCentersByTeam(team.getName()));
        team.setBallLosses(matchRepository.findBallLossesByTeam(team.getName()));

        // matches
        team.setWonMatches(matchRepository.findWonMatchesByTeam(team.getName()));
        team.setLostMatches(matchRepository.findLostMatchesByTeam(team.getName()));
        team.setDrawMatches(matchRepository.findDrawMatchesByTeam(team.getName()));
    }

    // --------------------------------------- PLAYER CRUD OPERATIONS
    // ---------------------------------------

    public Page<Player> findAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Optional<Player> findPlayerById(long id) {
        return playerRepository.findById(id);
    }

    public Optional<PlayerMatch> findPlayerMatchById(long id) {
        return playerMatchRepository.findById(id);
    }

    public Optional<Player> findPlayerByName(String name) {
        return playerRepository.findByNameIgnoreCase(name);
    }

    public Page<Player> findPlayersByLeague(League league, Pageable pageable) {
        return playerRepository.findPlayersByLeague(league, pageable);
    }

    public Page<Player> findPlayersByTeam(Team team, Pageable pageable) {
        return playerRepository.findPlayersByTeam(team, pageable);
    }

    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void createPlayerMatch(PlayerMatch playerMatch) {
        playerMatchRepository.save(playerMatch);
    }

    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    public void deletePlayerMatch(PlayerMatch playerMatch) {
        playerMatchRepository.delete(playerMatch);
    }

    public void updatePlayer(long id, Player modPlayer) {
        Player oldPlayer = findPlayerById(id).get();

        modPlayer.setLeague(oldPlayer.getLeague());
        modPlayer.setTeam(oldPlayer.getTeam());

        playerRepository.save(modPlayer);
    }

    public void updatePlayerMatch(long id, PlayerMatch modPlayer) {
        PlayerMatch oldPlayer = findPlayerMatchById(id).get();

        modPlayer.setMatch(oldPlayer.getMatch());
        modPlayer.setPlayer(oldPlayer.getPlayer());

        playerMatchRepository.save(modPlayer);
    }

    // To automaticaly update the players stats
    public void updatePlayerInfo(Player player) {

        player.setTotalShoots(playerMatchRepository.shootsByPlayer(player.getId()));
        player.setTotalGoals(playerMatchRepository.goalsByPlayer(player.getId()));
        player.setPenaltys(playerMatchRepository.penaltysByPlayer(player.getId()));
        player.setFaultsReceived(playerMatchRepository.faultsReceivedByPlayer(player.getId()));
        player.setOffsides(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setCommitedFaults(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setRecovers(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setDuels(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setWonDuels(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setYellowCards(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setRedCards(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setPasses(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setGoodPasses(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setShortPasses(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setLongPasses(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setAssists(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setDribles(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setCenters(playerMatchRepository.offsidesByPlayer(player.getId()));
        player.setBallLosses(playerMatchRepository.offsidesByPlayer(player.getId()));
    }

    public Page<Player> findPlayersByUser(User u, Pageable pageable) {
        return playerRepository.findAllByUser(u, pageable);
    }

    // --------------------------------------- MATCH CRUD OPERATIONS
    // ---------------------------------------

    public Page<Match> findAllMatches(Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    public Optional<Match> findMatchById(long id) {
        return matchRepository.findById(id);
    }

    public Page<Match> findMatchesByLeague(League league, Pageable pageable) {
        return matchRepository.findAllByLeague(league, pageable);
    }

    public Page<Match> findMatchesByTeam(Team team, Pageable pageable) {
        return matchRepository.findAllByTeam(team, pageable);
    }

    public void createMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(Match match) {
        matchRepository.delete(match);
    }

    public void updateMatch(long id, Match modMatch) {
        Match oldMatch = findMatchById(id).get();

        modMatch.setLeague(oldMatch.getLeague());
        modMatch.setTeam1(oldMatch.getTeam1());
        modMatch.setTeam2(oldMatch.getTeam2());

        matchRepository.save(modMatch);
    }

    //To automatically update the match stats
    public void updateMatchInfo(Match match) {

        //team 1 update stats
        match.setShoots1(playerMatchRepository.shootsByTeam(match.getId(),match.getTeam1().getId()));
        match.setScores1(playerMatchRepository.goalsByTeam(match.getId(),match.getTeam1().getId()));
        match.setPenaltys1(playerMatchRepository.penaltysByTeam(match.getId(),match.getTeam1().getId()));
        match.setFaultsReceived1(playerMatchRepository.faultsReceivedByTeam(match.getId(),match.getTeam1().getId()));
        match.setOffsides1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setCommitedFaults1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setRecovers1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setDuels1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setWonDuels1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setYellowCards1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setRedCards1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setPasses1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setGoodPasses1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setShortPasses1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setLongPasses1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setAssists1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setDribles1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setCenters1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        match.setBallLosses1(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam1().getId()));
        
        //team 2 update stats
        match.setShoots2(playerMatchRepository.shootsByTeam(match.getId(),match.getTeam2().getId()));
        match.setScores2(playerMatchRepository.goalsByTeam(match.getId(),match.getTeam2().getId()));
        match.setPenaltys2(playerMatchRepository.penaltysByTeam(match.getId(),match.getTeam2().getId()));
        match.setFaultsReceived2(playerMatchRepository.faultsReceivedByTeam(match.getId(),match.getTeam2().getId()));
        match.setOffsides2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setCommitedFaults2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setRecovers2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setDuels2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setWonDuels2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setYellowCards2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setRedCards2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setPasses2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setGoodPasses2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setShortPasses2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setLongPasses2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setAssists2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setDribles2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setCenters2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setBallLosses2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));

        updateTeamInfo(match.getTeam1());
        updateTeamInfo(match.getTeam2());
    }

}
