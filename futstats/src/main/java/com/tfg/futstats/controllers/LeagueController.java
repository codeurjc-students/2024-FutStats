package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import com.tfg.futstats.controllers.dtos.LeagueDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.errors.ElementNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {

    @Autowired
    RestService restService;

    // ------------------------------- League CRUD operations

    @GetMapping("/")
    public ResponseEntity<List<League>> getLeagues() {
        return ResponseEntity.ok(restService.findAllLeagues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable long id) {
        League league = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(league);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<League> getLeagueByName(@PathVariable String name) {
        League league = restService.findLeagueByName(name)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        return ResponseEntity.ok(league);
    }

     @GetMapping("/{leagueId}/teams")
    public ResponseEntity<List<Team>> getTeams(@PathVariable long leagueId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findTeamsByLeague(league));
    }

    @GetMapping("/{leaguesId}/players")
    public ResponseEntity<List<Player>> getPlayersByLeague(@PathVariable long leagueId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findPlayersByLeague(league));
    }

    @GetMapping("/{leaguesId}/teams/{teamId}/players")
    public ResponseEntity<List<Player>> getPlayers(@PathVariable long leagueId, @PathVariable long teamId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("no existe una liga con ese id"));
        
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("no existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findPlayersByTeam(team));
    }

    @GetMapping("/{leaguesId}/matches")
    public ResponseEntity<List<Match>> getMatchesByLeague(@PathVariable long leagueId){
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findMatchesByLeague(league));
    }

    @GetMapping("/{leaguesId}/teams/{teamId}/matches")
    public ResponseEntity<List<Match>> getMatches(@PathVariable long leagueId, @PathVariable long teamId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));
    
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findMatchesByTeam(team));
    }

    // From this point the only one that can use this methods is the admin, so we
    // have to create security for that

    @PostMapping("/")
    public ResponseEntity<League> postLeagues(HttpServletRequest request, @RequestBody LeagueDTO league) {
        // We don`t need this because is redundant, is already controlled in
        // SecurityConfig

        League newLeague = new League(league);

        restService.createLeague(newLeague);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newLeague.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLeague);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<League> deleteLeagues(HttpServletRequest request, @PathVariable long id) {
        // We don`t need this because is redundant, is already controlled in
        // SecurityConfig

        League league = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        restService.deleteLeague(league);

        return ResponseEntity.ok(league);

        // if the league ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @PutMapping("/{id}")
    public ResponseEntity<League> putLeagues(HttpServletRequest request, @PathVariable long id, @RequestBody LeagueDTO leagueDto) {
        League oldLeague = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        League newLeague = new League(leagueDto);

        newLeague.setId(oldLeague.getId());                     

        restService.updateLeague(newLeague, oldLeague, leagueDto);

        return ResponseEntity.ok(newLeague);

        // if the league ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }
}
