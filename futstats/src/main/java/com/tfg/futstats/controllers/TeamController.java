package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.TeamDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
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

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1")
public class TeamController {

    @Autowired
    RestService restService;
    
    // ------------------------------- Team CRUD operations
    // --------------------------------------------
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(restService.findAllTeams());
    }

    @GetMapping("leagues/{leagueId}/teams")
    public ResponseEntity<Page<Team>> getTeams(@PathVariable long leagueId, Pageable pageable) {
        Optional<League> league = restService.findLeagueById(leagueId);

        return ResponseEntity.ok(restService.findTeamsByLeague(league.orElseThrow(() -> new ElementNotFoundException("")),PageRequest.of(pageable.getPageNumber(),10)));
    }

    @GetMapping("/teams/{id}") 
    public ResponseEntity<Team> getTeam(@PathVariable long id) {
        Optional<Team> team = restService.findTeamById(id);

        return ResponseEntity.ok(team.orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id")));
    }

    @GetMapping("/teams/{name}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String name) {
        Optional<Team> team = restService.findTeamByName(name);

        return ResponseEntity.ok(team.orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre")));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/teams")
    public ResponseEntity<Team> postTeams(HttpServletRequest request, @RequestBody TeamDTO team) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Team newTeam = new Team(team);

        League league = restService.findLeagueByName(team.getLeague()).orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        newTeam.setLeague(league);
        league.setTeam(newTeam);

        restService.createTeam(newTeam);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTeam.getId()).toUri();

        return ResponseEntity.created(location).body(newTeam);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Team> deleteTeams(HttpServletRequest request, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Team team = restService.findTeamById(id).orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        restService.deleteTeam(team);
        
        return ResponseEntity.ok(team);

        //if the team ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> putTeams(HttpServletRequest request, @PathVariable long id, @RequestBody TeamDTO newTeam) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/
        
        Team team = restService.findTeamById(id).orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        Team modTeam = new Team(newTeam);

        restService.updateTeam(id, modTeam);
         return ResponseEntity.ok(team);

        //if the team ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }
}
