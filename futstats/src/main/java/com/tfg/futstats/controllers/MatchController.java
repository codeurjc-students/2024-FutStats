package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.MatchDTO;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.services.RestService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/leagues/{leaguesId}/matches")
    public ResponseEntity<Page<Match>> getMatchesByLeague(@PathVariable long leagueId, Pageable pageable) {
        return ResponseEntity.ok(restService.findMatchesByLeague(leagueId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/teams/{teamsId}/matches")
    public ResponseEntity<Page<Match>> getMatchesByTeam(@PathVariable long teamId, Pageable pageable) {
        return ResponseEntity.ok(restService.findMatchesByTeam(teamId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}/matches")
    public ResponseEntity<Page<Match>> getMatches(@PathVariable long leagueId, @PathVariable long teamId, Pageable pageable) {
        return ResponseEntity.ok(restService.findMatchesByTeam(teamId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/matches")
    public ResponseEntity<Match> postMatches(@RequestBody MatchDTO match) {
        Match newMatch = new Match(match);

        restService.createMatch(newMatch);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMatch);
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Match> deleteMatches(@PathVariable long id) {
        Optional<Match> match = restService.findMatchById(id);
        if (match.isPresent()) {
            restService.deleteMatch(match.get());
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
