package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.LeagueRepository;
import com.tfg.futstats.repositories.PlayerRepository;
import com.tfg.futstats.repositories.TeamRepository;

@Service
public class RestService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // --------------------------------------- LEAGUE CRUD OPERATIONS
    // ---------------------------------------

    public Page<League> findAllLeagues() {
        return leagueRepository.findAll(5);
    }

    public void createLeague(League league) {
        leagueRepository.save(league);
    }

    public void deleteLeague(League league) {
        leagueRepository.delete(league);
    }

    public void updateLeague(long id, League modLeague) {
        League oldLeague = findLeagueById(id);

        modLeague.setTeams(oldLeague.getTeams());
        modLeague.setMatches(oldLeague.getMatches());
        modLeague.setPlayers(oldLeague.getPlayers());
        modLeague.setUsers(oldLeague.getUsers());

        leagueRepository.save(modLeague);
    }

    public Page<Team> findLeaguesByUser(User u)
    {
        return leagueRepository.findAllByUser(u,5)
    }

    // --------------------------------------- TEAM CRUD OPERATIONS
    // ---------------------------------------

    public Page<Team> findAllTeams() {
        return teamRepository.findAll(5);
    }

    public void createTeam(Team team) {
        teamepository.save(team);
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    public void updateTeam(long id, Team modTeam) {
        Team oldTeam = findTeamById(id);

        modTeam.setLeague(oldTeam.getLeague());
        modTeam.setMatches(oldTeam.getMatches());
        modTeam.setPlayers(oldTeam.getPlayers());
        modTeam.setUsers(oldTeam.getUsers());

        teamRepository.save(modTeam);
    }

    public Page<Team> findTeamsByUser(User u)
    {
        return teamRepository.findAllByUser(u,5)
    }

    // --------------------------------------- PLAYER CRUD OPERATIONS
    // ---------------------------------------

    public Page<Player> findAllPlayers() {
        return playerRepository.findAll(5);
    }

    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    public void updatePlayer(long id, Player modPlayer) {
        Player oldPlayer = findPlayerById(id);

        modPlayer.setLeague(oldPlayer.getLeague());
        modPlayer.setTeam(oldPlayer.getTeam());
        modPlayer.setUsers(oldPlayer.getUsers());

        playerRepository.save(modPlayer);
    }

    public Page<Team> findPlayersByUser(User u)
    {
        return playerRepository.findAllByUser(u,5)
    }

    // --------------------------------------- MATCH CRUD OPERATIONS
    // ---------------------------------------

    public Page<Match> findAllMatches() {
        return matchRepository.findAll(5);
    }

    public void createMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(Match match) {
        matchRepository.delete(match);
    }

    public void updateMatch(long id, Match modMatch) {
        Match oldMatch = findMatchById(id);

        modMatch.setLeague(oldMatch.getLeague());
        modMatch.setTeam1(oldMatch.getTeam1());
        modMatch.setTeam2(oldMatch.getTeam2());

        matchRepository.save(modMatch);
    }

}
