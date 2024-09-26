package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.LeagueDTO;
import com.tfg.futstats.models.League;
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
public class LeagueController {

    @Autowired
    RestService restService;

    // ------------------------------- League CRUD operations
    // --------------------------------------------
    @GetMapping("/leagues")
    public ResponseEntity<Page<League>> getLeagues(Pageable pageable) {
        return ResponseEntity.ok(restService.findAllLeagues(PageRequest.of(pageable.getPageNumber(), 5)));
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
    
}
