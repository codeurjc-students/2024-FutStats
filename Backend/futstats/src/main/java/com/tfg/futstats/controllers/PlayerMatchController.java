package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.services.RestService;

@RestController
@RequestMapping("/api/v1/playerMatches")
public class PlayerMatchController {

     @Autowired
    RestService restService;

    // ------------------------------- PlayerMatch CRUD operations
    // --------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<PlayerMatch> getPlayerMatchById(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        return ResponseEntity.ok(playerMatch);
    }

    @GetMapping("/{id}/player")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        return ResponseEntity.ok(playerMatch.getPlayer());
    }

    @GetMapping("/{id}/match")
    public ResponseEntity<Match> getMatch(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        return ResponseEntity.ok(playerMatch.getMatch());
    }
}

