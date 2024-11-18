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

    // --------------------------------------- LEAGUE CRUD OPERATIONS
    // ---------------------------------------

    public List<League> findAllLeagues() {
        return leagueRepository.findAll();
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

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
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
        League league = team.getLeague();
        league.deleteTeam(team);
        leagueRepository.save(league);
        teamRepository.delete(team);
    }

    public void updateTeam(long id, Team modTeam) {
        Team oldTeam = findTeamById(id).get();

        modTeam.setId(id);
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

        teamRepository.save(team);
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
        League league = player.getLeague();
        Team team = player.getTeam();

        league.deletePlayer(player);
        team.deletePlayer(player);

        leagueRepository.save(league);
        teamRepository.save(team);
    
        playerRepository.delete(player);
    }

    public void deletePlayerMatch(PlayerMatch playerMatch,long id) {
        Match match = matchRepository.findById(id).get();
        Player player = playerMatch.getPlayer();

        match.deletePlayerMatch(playerMatch);
        player.deletePlayerMatch(playerMatch);

        matchRepository.save(match);
        playerRepository.save(player);

        //Call the update team and match methods

        playerMatchRepository.deleteById(playerMatch.getId());
    }

    public void updatePlayer(long id, Player modPlayer) {
        Player oldPlayer = findPlayerById(id).get();

        modPlayer.setLeague(oldPlayer.getLeague());
        modPlayer.setTeam(oldPlayer.getTeam());

        playerRepository.save(modPlayer);
    }

    public void updatePlayerMatch(long id, PlayerMatch modPlayer) {
        playerMatchRepository.save(modPlayer);
        updatePlayerInfo(modPlayer.getPlayer());
        updateMatchInfo(modPlayer.getMatch());
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

    public Page<Player> findPlayersByUser(User u, Pageable pageable) {
        return playerRepository.findAllByUser(u, pageable);
    }

    // --------------------------------------- MATCH CRUD OPERATIONS
    // ---------------------------------------

    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> findMatchById(long id) {
        return matchRepository.findById(id);
    }

    public Page<Match> findMatchesByLeague(League league, Pageable pageable) {
        return matchRepository.findAllByLeague(league.getName(), pageable);
    }

    public Page<Match> findMatchesByTeam(Team team, Pageable pageable) {
        return matchRepository.findAllByTeam(team.getName(), pageable);
    }

    public void createMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(Match match) {
        League league = match.getLeague();
        Team team1 = match.getTeam1();
        Team team2 = match.getTeam2();

        league.deleteMatch(match);
        team1.deleteMatch(match);
        team2.deleteMatch(match);

        leagueRepository.save(league);
        teamRepository.save(team1);
        teamRepository.save(team2);

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
        match.setCommitedFaults1(playerMatchRepository.commitedFaultsByTeam(match.getId(),match.getTeam1().getId()));
        match.setRecovers1(playerMatchRepository.recoversByTeam(match.getId(),match.getTeam1().getId()));
        match.setDuels1(playerMatchRepository.driblesByTeam(match.getId(),match.getTeam1().getId()));
        match.setWonDuels1(playerMatchRepository.wonDuelsByTeam(match.getId(),match.getTeam1().getId()));
        match.setYellowCards1(playerMatchRepository.yellowCardsByTeam(match.getId(),match.getTeam1().getId()));
        match.setRedCards1(playerMatchRepository.redCardsByTeam(match.getId(),match.getTeam1().getId()));
        match.setPasses1(playerMatchRepository.passesByTeam(match.getId(),match.getTeam1().getId()));
        match.setGoodPasses1(playerMatchRepository.goodPassesByTeam(match.getId(),match.getTeam1().getId()));
        match.setShortPasses1(playerMatchRepository.shortPassesByTeam(match.getId(),match.getTeam1().getId()));
        match.setLongPasses1(playerMatchRepository.longPassesByTeam(match.getId(),match.getTeam1().getId()));
        match.setAssists1(playerMatchRepository.assistsByTeam(match.getId(),match.getTeam1().getId()));
        match.setDribles1(playerMatchRepository.driblesByTeam(match.getId(),match.getTeam1().getId()));
        match.setCenters1(playerMatchRepository.centersByTeam(match.getId(),match.getTeam1().getId()));
        match.setBallLosses1(playerMatchRepository.ballLossesByTeam(match.getId(),match.getTeam1().getId()));
        
        //team 2 update stats
        match.setShoots2(playerMatchRepository.shootsByTeam(match.getId(),match.getTeam2().getId()));
        match.setScores2(playerMatchRepository.goalsByTeam(match.getId(),match.getTeam2().getId()));
        match.setPenaltys2(playerMatchRepository.penaltysByTeam(match.getId(),match.getTeam2().getId()));
        match.setFaultsReceived2(playerMatchRepository.faultsReceivedByTeam(match.getId(),match.getTeam2().getId()));
        match.setOffsides2(playerMatchRepository.offsidesByTeam(match.getId(),match.getTeam2().getId()));
        match.setCommitedFaults2(playerMatchRepository.commitedFaultsByTeam(match.getId(),match.getTeam2().getId()));
        match.setRecovers2(playerMatchRepository.recoversByTeam(match.getId(),match.getTeam2().getId()));
        match.setDuels2(playerMatchRepository.driblesByTeam(match.getId(),match.getTeam2().getId()));
        match.setWonDuels2(playerMatchRepository.wonDuelsByTeam(match.getId(),match.getTeam2().getId()));
        match.setYellowCards2(playerMatchRepository.yellowCardsByTeam(match.getId(),match.getTeam2().getId()));
        match.setRedCards2(playerMatchRepository.redCardsByTeam(match.getId(),match.getTeam2().getId()));
        match.setPasses2(playerMatchRepository.passesByTeam(match.getId(),match.getTeam2().getId()));
        match.setGoodPasses2(playerMatchRepository.goodPassesByTeam(match.getId(),match.getTeam2().getId()));
        match.setShortPasses2(playerMatchRepository.shortPassesByTeam(match.getId(),match.getTeam2().getId()));
        match.setLongPasses2(playerMatchRepository.longPassesByTeam(match.getId(),match.getTeam2().getId()));
        match.setAssists2(playerMatchRepository.assistsByTeam(match.getId(),match.getTeam2().getId()));
        match.setDribles2(playerMatchRepository.driblesByTeam(match.getId(),match.getTeam2().getId()));
        match.setCenters2(playerMatchRepository.centersByTeam(match.getId(),match.getTeam2().getId()));
        match.setBallLosses2(playerMatchRepository.ballLossesByTeam(match.getId(),match.getTeam2().getId()));

        matchRepository.save(match);

        updateTeamInfo(match.getTeam1());
        updateTeamInfo(match.getTeam2());
    }

}
