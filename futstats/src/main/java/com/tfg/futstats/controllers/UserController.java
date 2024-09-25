package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.controllers.DTOs.LeagueDTO;
import com.tfg.futstats.controllers.DTOs.MatchDTO;
import com.tfg.futstats.controllers.DTOs.PlayerDTO;
import com.tfg.futstats.controllers.DTOs.TeamDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import java.net.URI;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    RestService restService;

    // ------------------------------- League CRUD operations
    // --------------------------------------------
    @GetMapping("/leagues")
    public ResponseEntity<Page<League>> getLeagues() {
        return ResponseEntity.ok(restService.findAllLeagues(), PageRequest.of(pageable.getPageNumber(), 5));
    }

    @GetMapping("/leagues/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable long id) {
        Optional<League> league = restService.findLeagueById(id);

        return ResponseEntity.ok(league.orElseThrow());
    }

    @GetMapping("/leagues/{name}")
    public ResponseEntity<League> getLeagueByName(@PathVariable String name) {
        Optional<League> league = restService.findLeagueByName(name);

        return ResponseEntity.ok(league.orElseThrow());
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/leagues")
    public ResponseEntity<League> postLeagues(@RequestBody LeagueDTO league) {
        League newLeague = new League(league);

        restService.createLeague(newLeague);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newLeague.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLeague);
    }

    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<League> deleteLeagues(@PathVariable long id) {
        Optional<League> league = restService.findLeagueById(id);
        if (league.isPresent()) {
            restService.deleteLeague(league.get());
            return ResponseEntity.ok(league.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/leagues/{id}")
    public ResponseEntity<League> putLeagues(@PathVariable long id, @RequestBody LeagueDTO newLeague) {
        Optional<League> league = restService.findLeagueById(id);

        League modLeague = new League(newLeague);

        if (league.isPresent()) {
            modLeague.setId(id);
            restService.updateLeague(id, modLeague);
            return ResponseEntity.ok(league.get());
        }

        return ResponseEntity.notFound().build();
    }

    // ------------------------------- Team CRUD operations
    // --------------------------------------------
    @GetMapping("/teams")
    public ResponseEntity<Page<Team>> getAllTeams() {
        return ResponseEntity.ok(restService.findAllTeams(),PageRequest.of(pageable.getPageNumber(),10);
    }

    @GetMapping("leagues/{leagueId}/teams")
    public ResponseEntity<Page<Team>> getTeams(@PathVariable long leagueId) {
        Optional<League> league = restService.findLeagueById(leagueId);

        return ResponseEntity.ok(league.orElseThrow().getTeams());
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable long id) {
        Optional<League> team = restService.findTeamById(id);

        return ResponseEntity.ok(team.orElseThrow());
    }

    @GetMapping("/teams/{name}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String name) {
        Optional<Team> team = restService.findTeamByNameIgnoreCase(name);

        return ResponseEntity.ok(team.orElseThrow());
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/teams")
    public ResponseEntity<Team> postTeams(@RequestBody TeamDTO team) {
        Team newTeam = new Team(team);

        restService.createTeam(newTeam);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTeam.getId())
                .toUri();

        return ResponseEntity.created(location).body(newTeam);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Team> deleteTeams(@PathVariable long id) {
        Optional<Team> team = restService.findTeamById(id);
        if (team.isPresent()) {
            restService.deleteLeague(team.get());
            return ResponseEntity.ok(team.get());
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> putTeams(@PathVariable long id, @RequestBody TeamDTO newTeam) {
        Optional<Team> team = restService.findTeamById(id);

        Team modTeam = new Team(newTeam);

        if (team.isPresent()) {
            modTeam.setId(id);
            restService.updateLeague(id, modTeam);
            return ResponseEntity.ok(team.get());
        }

        return ResponseEntity.notFound().build();
    }

    // ------------------------------- Player CRUD operations
    // --------------------------------------------
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAllPlayers() {
        return ResponseEntity.ok(restService.findAllPlayers(), PageRequest.of(pageable.getPageNumber(), 10));
    }

    @GetMapping("/leagues/{leaguesId}/players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId) {
        Optional<League> league = leagueService.findLeagueById(leagueId);

        return ResponseEntity.ok(league.orElseThrow().getPlayers());
    }

    @GetMapping("/teams/{teamsId}/players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getPlayers());
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId, @PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getPlayers());
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Optional<Player> player = leagueService.findPlayerById(id);

        return ResponseEntity.ok(player.orElseThrow());
    }

    @GetMapping("/player/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name) {
        Optional<Player> player = leagueService.findPlayerByNameIgnoreCase(name);

        return ResponseEntity.ok(player.orElseThrow());
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/players")
    public ResponseEntity<Player> postPlayers(@RequestBody PlayerDTO player) {
        Player newPlayer = new Player(player);

        restService.createPlayer(newPlayer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayer);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayers(@PathVariable long id) {
        Optional<Player> player = restService.findPlayerById(player);
        if (player.isPresent()) {
            restService.deletePlayer(player.get());
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> putPlayers(@PathVariable long id, @RequestBody PlayerDTO newPlayer) {
        Optional<Player> player = restService.findPlayerById(id);

        Player modPlayer = new Player(newPlayer);

        if (player.isPresent()) {
            modPlayer.setId(id);
            restService.updatePlayer(id, modPlayer);
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }

    // ------------------------------- Match CRUD operations
    // --------------------------------------------
    @GetMapping("/matches")
    public ResponseEntity<Page<Match>> getMatches() {
        return ResponseEntity.ok(restService.findAllMatches(), PageRequest.of(pageable.getPageNumber(), 10));
    }

    @GetMapping("/leagues/{leaguesId}/matches")
    public ResponseEntity<Page<Match>> getMatches(@PathVariable long leagueId) {
        Optional<League> league = leagueService.findLeagueById(leagueId);

        return ResponseEntity.ok(league.orElseThrow().getMatches());
    }

    @GetMapping("/teams/{teamsId}/matches")
    public ResponseEntity<Page<Match>> getMatches(@PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getMatches());
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}players")
    public ResponseEntity<Page<Match>> getMatches(@PathVariable long leagueId, @PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getMatches());
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/matches")
    public ResponseEntity<Match> postMatches(@RequestBody MatchDTO match) {
        Match newMatch = new Match(match);

        restService.createMatch(match);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMatch);
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Match> deleteMatches(@PathVariable long id) {
        Optional<Match> match = restService.findPlayerById(match);
        if (match.isPresent()) {
            restService.deletePlayer(match.get());
            return ResponseEntity.ok(match.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> putMatches(@PathVariable long id, @RequestBody MatchDTO newMatch) {
        Optional<Match> match = restService.findMatchById(id);

        Match modMatch = new Match(newMatch);

        if (match.isPresent()) {
            modMatch.setId(id);
            restService.updateMatch(id, modMatch);
            return ResponseEntity.ok(match.get());
        }

        return ResponseEntity.notFound().build();
    }

}
