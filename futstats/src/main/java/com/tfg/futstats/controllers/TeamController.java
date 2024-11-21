package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.tfg.futstats.controllers.dtos.team.TeamCreationDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.services.RestService;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    RestService restService;
    
    // ------------------------------- Team CRUD operations

    @GetMapping("/")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(restService.findAllTeams());
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Team> getTeam(@PathVariable long id) {
        Team team = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        return ResponseEntity.ok(team);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String name) {
        Team team = restService.findTeamByName(name)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        return ResponseEntity.ok(team);
    }

    @GetMapping("/{teamsId}/players")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable long teamId) {
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("no existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findPlayersByTeam(team));
    }

    @GetMapping("/{teamsId}/matches")
    public ResponseEntity<List<Match>> getMatchesByTeam(@PathVariable long teamId) {
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findMatchesByTeam(team));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/")
    public ResponseEntity<Team> postTeams(HttpServletRequest request, @RequestBody TeamCreationDTO team) {
        Team newTeam = new Team(team);

        League league = restService.findLeagueByName(team.getLeague()).orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        restService.createTeam(newTeam, league);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTeam.getId()).toUri();

        return ResponseEntity.created(location).body(newTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> deleteTeams(HttpServletRequest request, @PathVariable long id) {
        Team team = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        restService.deleteTeam(team);
        
        return ResponseEntity.ok(team);

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> putTeams(HttpServletRequest request, @PathVariable long id, @RequestBody TeamUpdateDTO teamDto) {
        Team oldTeam = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        Team newTeam = new Team(teamDto);

        League league = new League();

        //We have to do this comprobation here because we have to know if the league exists
        if(teamDto.getLeague() == null){
            league = oldTeam.getLeague();
        }else{
            league = restService.findLeagueByName(teamDto.getLeague())
                    .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));
        }

        restService.updateTeam(newTeam, oldTeam, teamDto, league);
        
        return ResponseEntity.ok(newTeam);

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }
}