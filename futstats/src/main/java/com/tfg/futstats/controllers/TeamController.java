package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.controllers.DTOs.TeamDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TeamController {

    @Autowired
    RestService restService;
    
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
}
