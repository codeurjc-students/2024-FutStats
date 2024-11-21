package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.LeagueDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.match.MatchCreationDTO;
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

    public void updateLeague(League oldLeague, League newLeague, LeagueDTO leagueDto) {
        newLeague.setId(oldLeague.getId());

        //If the admin has not intruduced any data, it will get
        //the data from the unmodified league
        if(leagueDto.getName() == null){
            newLeague.setName(oldLeague.getName());
        }
        if(leagueDto.getPresident() == null){
            newLeague.setPresident(oldLeague.getPresident());
        }
        if(leagueDto.getNationality() == null){
            newLeague.setNationality(oldLeague.getNationality());
        }

        newLeague.setTeams(oldLeague.getTeams());
        newLeague.setMatches(oldLeague.getMatches());
        newLeague.setPlayers(oldLeague.getPlayers());
        newLeague.setUsers(oldLeague.getUsers());
    
        leagueRepository.save(newLeague);
    }

    public List<League> findLeaguesByUser(User u) {
        return leagueRepository.findAllByUsers(u);
    }

    // --------------------------------------- TEAM CRUD OPERATIONS
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> findTeamById(long id) {
        return teamRepository.findById(id);
    }

    public Optional<Team> findTeamByName(String name) {
        return teamRepository.findByNameIgnoreCase(name);
    }

    public List<Team> findTeamsByLeague(League league) {
        return teamRepository.findTeamsByLeague(league);
    }

    public List<Team> findTeamsByUser(User u) {
        return teamRepository.findAllByUsers(u);
    }

    public void createTeam(Team team,League league) {
        team.setLeague(league);
        league.setTeam(team);
        teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        League league = team.getLeague();
        league.deleteTeam(team);
        leagueRepository.save(league);
        teamRepository.delete(team);
    }

    public void updateTeam(Team newTeam, Team oldTeam, TeamUpdateDTO teamDto, League league) {
        newTeam.setId(oldTeam.getId());

        //If the admin has not intruduced any data, it will get
        //the data from the unmodified team
        if(teamDto.getName() == null){
            newTeam.setName(oldTeam.getName());
        }
        if(teamDto.getTrophies() == 0){
            newTeam.setTrophies(oldTeam.getTrophies());
        }
        if(teamDto.getNationality() == null){
            newTeam.setNationality(oldTeam.getNationality());
        }
        if(teamDto.getTrainer() == null){
            newTeam.setTrainer(oldTeam.getTrainer());
        }
        if(teamDto.getSecondTrainer() == null){
            newTeam.setSecondTrainer(oldTeam.getSecondTrainer());
        }
        if(teamDto.getPresident() == null){
            newTeam.setPresident(oldTeam.getPresident());
        }
        if(teamDto.getStadium() == null){
            newTeam.setStadium(oldTeam.getStadium());
        }
        
        newTeam.setLeague(league);
        league.setTeam(newTeam);
        
        leagueRepository.save(league);
        
        updateTeamInfo(newTeam);
    }

    public void updateTeamInfo(Team team) {
        //offensive
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
        team.setPossesion(matchRepository.findAveragePossessionByTeam(team.getId()));
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
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> findPlayerById(long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findPlayerByName(String name) {
        return playerRepository.findByNameIgnoreCase(name);
    }

    public Optional<PlayerMatch> findPlayerMatchById(long id) {
        return playerMatchRepository.findById(id);
    }

    public List<Player> findPlayersByLeague(League league) {
        return playerRepository.findPlayersByLeague(league);
    }

    public List<Player> findPlayersByTeam(Team team) {
        return playerRepository.findPlayersByTeam(team);
    }

    public List<Player> findPlayersByUser(User u) {
        return playerRepository.findAllByUsers(u);
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

        player.setPlayerMatch(newPlayerMatch);
        match.setPlayerMatch(newPlayerMatch);

        playerMatchRepository.save(newPlayerMatch);
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

    public void updatePlayer(Player oldPlayer, Player newPlayer, PlayerDTO playerDto, League league, Team team) {
        newPlayer.setId(oldPlayer.getId());

        //If the admin has not intruduced any data, it will get
        //the data from the unmodified league
        if(playerDto.getName() == null){
            newPlayer.setName(oldPlayer.getName());
        }
        if(playerDto.getAge() == 0){
            newPlayer.setAge(oldPlayer.getAge());
        }
        if(playerDto.getNationality() == null){
            newPlayer.setNationality(oldPlayer.getNationality());
        }
        if(playerDto.getPosition() == null){
            newPlayer.setPosition(oldPlayer.getPosition());
        }

        newPlayer.setPlayerMatches(oldPlayer.getPlayerMatches());
        newPlayer.setUsers(oldPlayer.getUsers());

        updatePlayerInfo(newPlayer);

        league.setPlayer(newPlayer);
        team.setPlayer(newPlayer);
        
        leagueRepository.save(league);
        teamRepository.save(team);
    }

    public void updatePlayerMatch(PlayerMatch oldPlayerMatch, PlayerMatch newPlayerMatch, PlayerMatchDTO playerMatchDto, Match match, Player player) {
        newPlayerMatch.setId(oldPlayerMatch.getId());

        if(playerMatchDto.getShoots() == 0){
            newPlayerMatch.setShoots(oldPlayerMatch.getShoots());
        }
        if(playerMatchDto.getGoals() == 0){
            newPlayerMatch.setGoals(oldPlayerMatch.getGoals());
        }
        if(playerMatchDto.getPenaltys() == 0){
            newPlayerMatch.setPenaltys(oldPlayerMatch.getPenaltys());
        }
        if(playerMatchDto.getFaultsReceived() == 0){
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if(playerMatchDto.getOffsides() == 0){
            newPlayerMatch.setOffsides(oldPlayerMatch.getOffsides());
        }
        if(playerMatchDto.getFaultsReceived() == 0){
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if(playerMatchDto.getCommitedFaults() == 0){
            newPlayerMatch.setCommitedFaults(oldPlayerMatch.getCommitedFaults());
        }
        if(playerMatchDto.getRecovers() == 0){
            newPlayerMatch.setRecovers(oldPlayerMatch.getRecovers());
        }
        if(playerMatchDto.getFaultsReceived() == 0){
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if(playerMatchDto.getFaultsReceived() == 0){
            newPlayerMatch.setFaultsReceived(oldPlayerMatch.getFaultsReceived());
        }
        if(playerMatchDto.getDuels() == 0){
            newPlayerMatch.setDuels(oldPlayerMatch.getDuels());
        }
        if(playerMatchDto.getWonDuels() == 0){
            newPlayerMatch.setWonDuels(oldPlayerMatch.getWonDuels());
        }
        if(playerMatchDto.getYellowCards() == 0){
            newPlayerMatch.setYellowCards(oldPlayerMatch.getYellowCards());
        }
        if(playerMatchDto.getRedCards() == 0){
            newPlayerMatch.setRedCards(oldPlayerMatch.getRedCards());
        }
        if(playerMatchDto.getPasses() == 0){
            newPlayerMatch.setPasses(oldPlayerMatch.getPasses());
        }
        if(playerMatchDto.getGoodPasses() == 0){
            newPlayerMatch.setGoodPasses(oldPlayerMatch.getGoodPasses());
        }
        if(playerMatchDto.getShortPasses() == 0){
            newPlayerMatch.setShortPasses(oldPlayerMatch.getShortPasses());
        }
        if(playerMatchDto.getLongPasses() == 0){
            newPlayerMatch.setLongPasses(oldPlayerMatch.getLongPasses());
        }
        if(playerMatchDto.getAssists() == 0){
            newPlayerMatch.setAssists(oldPlayerMatch.getAssists());
        }
        if(playerMatchDto.getDribles() == 0){
            newPlayerMatch.setDribles(oldPlayerMatch.getDribles());
        }
        if(playerMatchDto.getCenters() == 0){
            newPlayerMatch.setCenters(oldPlayerMatch.getCenters());
        }
        if(playerMatchDto.getBallLosses() == 0){
            newPlayerMatch.setBallLosses(oldPlayerMatch.getBallLosses());
        }
        if(playerMatchDto.getShootsReceived() == 0){
            newPlayerMatch.setShootsReceived(oldPlayerMatch.getShootsReceived());
        }
        if(playerMatchDto.getGoalsConceded() == 0){
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if(playerMatchDto.getGoalsConceded() == 0){
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if(playerMatchDto.getSaves() == 0){
            newPlayerMatch.setSaves(oldPlayerMatch.getSaves());
        }
        if(playerMatchDto.getGoalsConceded() == 0){
            newPlayerMatch.setGoalsConceded(oldPlayerMatch.getGoalsConceded());
        }
        if(playerMatchDto.getOutBoxSaves() == 0){
            newPlayerMatch.setOutBoxSaves(oldPlayerMatch.getOutBoxSaves());
        }
        if(playerMatchDto.getInBoxSaves() == 0){
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

    public void testingPlayerMatchAttributes(Player oldPlayer, Player newPlayer){

    }

    // --------------------------------------- MATCH CRUD OPERATIONS
    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> findMatchById(long id) {
        return matchRepository.findById(id);
    }

    public List<Match> findMatchesByLeague(League league) {
        return matchRepository.findAllByLeague(league.getName());
    }

    public List<Match> findMatchesByTeam(Team team) {
        return matchRepository.findAllByTeam(team.getId());
    }

    public void createMatch(Match newMatch, League league, Team team1, Team team2) {
        newMatch.setLeague(league);
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        newMatch.setName(team1.getName() +'/' + team2.getName());
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

    public void updateMatch(Match oldMatch, Match newMatch, MatchCreationDTO matchDto, League league, Team team1, Team team2) {
        newMatch.setId(oldMatch.getId());

        if(matchDto.getName() == null){
            newMatch.setName(oldMatch.getName());
        }
        if(matchDto.getPlace() == null){
            newMatch.setPlace(oldMatch.getPlace());
        }

        newMatch.setPlayerMatches(oldMatch.getPlayerMatches());
        
        newMatch.setLeague(league);
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);

        updateMatchInfo(newMatch);

        league.setMatch(newMatch);
        team1.setMatch(newMatch);
        team2.setMatch(newMatch);

        leagueRepository.save(league);
        teamRepository.save(team1);
        teamRepository.save(team2);
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
