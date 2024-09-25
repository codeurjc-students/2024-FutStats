package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.controllers.DTOs.MatchDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
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

public class MatchController {

     @Autowired
    RestService restService;

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
