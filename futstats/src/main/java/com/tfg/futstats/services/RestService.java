package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.TeamRepository;
import com.tfg.futstats.repositories.MatchRepository;
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

    // --------------------------------------- PLAYER CRUD OPERATIONS
    // ---------------------------------------

    public Page<Player> findAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Optional<Player> findPlayerById(long id) {
        return playerRepository.findById(id);
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

    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    public void updatePlayer(long id, Player modPlayer) {
        Player oldPlayer = findPlayerById(id).get();

        modPlayer.setLeague(oldPlayer.getLeague());
        modPlayer.setTeam(oldPlayer.getTeam());
        

        playerRepository.save(modPlayer);
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

}
