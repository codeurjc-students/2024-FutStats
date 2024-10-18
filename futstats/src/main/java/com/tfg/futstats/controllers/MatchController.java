package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.MatchDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1")
public class MatchController {

     @Autowired
    RestService restService;

    // ------------------------------- Match CRUD operations
    // --------------------------------------------
    @GetMapping("/matches")
    public ResponseEntity<Page<Match>> getMatches(Pageable pageable) {
        return ResponseEntity.ok(restService.findAllMatches(PageRequest.of(pageable.getPageNumber(), 10)));
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable long id,Pageable pageable) {
        return ResponseEntity.ok(restService.findMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id")));
    }

    @GetMapping("/leagues/{leaguesId}/matches")
    public ResponseEntity<Page<Match>> getMatchesByLeague(@PathVariable long leagueId, Pageable pageable){
        Optional<League> league = restService.findLeagueById(leagueId);

        return ResponseEntity.ok(restService.findMatchesByLeague(league.orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/teams/{teamsId}/matches")
    public ResponseEntity<Page<Match>> getMatchesByTeam(@PathVariable long teamId, Pageable pageable) {
        Optional<Team> team = restService.findTeamById(teamId);

        return ResponseEntity.ok(restService.findMatchesByTeam(team.orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}/matches")
    public ResponseEntity<Page<Match>> getMatches(@PathVariable long leagueId, @PathVariable long teamId, Pageable pageable) {
        Optional<Team> team = restService.findTeamById(teamId);

        Optional<League> league = restService.findLeagueById(leagueId);

        league.orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findMatchesByTeam(team.orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/matches")
    public ResponseEntity<Match> postMatches(HttpServletRequest request, @RequestBody MatchDTO match) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Match newMatch = new Match(match);

        restService.createMatch(newMatch);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMatch);
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Match> deleteMatches(HttpServletRequest request, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Match match = restService.findMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        restService.deleteMatch(match);
        return ResponseEntity.ok(match);
        
        //if the match ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> putMatches(HttpServletRequest request, @PathVariable long id, @RequestBody MatchDTO newMatch) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Match match = restService.findMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        Match modMatch = new Match(newMatch);

        modMatch.setId(id);

        //We update the team stats now that we are sure that the match exists
        restService.updateTeamInfo(modMatch.getTeam1());
        restService.updateTeamInfo(modMatch.getTeam2());

        restService.updateMatch(id, modMatch);

        return ResponseEntity.ok(match);

        //if the match ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }
}
