package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.controllers.DTOs.LeagueDTO;
import com.tfg.futstats.controllers.DTOs.PlayerDTO;
import com.tfg.futstats.controllers.DTOs.TeamDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;

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

    // ------------------------------- League CRUD operations
    // --------------------------------------------
    @GetMapping("/leagues")
    public ResponseEntity<Page<League>> getLeagues() {

    }

    @PostMapping("/leagues")
    public ResponseEntity<League> postLeagues(@RequestBody LeagueDTO league) {

    }

    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<League> deleteLeagues(@PathVariable long id) {

    }

    @PutMapping("/leagues/{id}")
    public ResponseEntity<League> putLeagues(@PathVariable long id, @RequestBody LeagueDTO newLeague) {

    }

    // ------------------------------- Team CRUD operations
    // --------------------------------------------
    @GetMapping("/teams")
    public ResponseEntity<Page<Team>> getAllTeams() {

    }

    @PostMapping("/teams")
    public ResponseEntity<Team> postTeams(@RequestBody TeamDTO team) {

    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Team> deleteTeams(@PathVariable long id) {

    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> putTeams(@PathVariable long id, @RequestBody TeamDTO newTeam) {

    }

    // ------------------------------- Player CRUD operations
    // --------------------------------------------
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAllPlayers() {

    }

    @PostMapping("/players")
    public ResponseEntity<Player> postPlayers(@RequestBody PlayerDTO player) {

    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayers(@PathVariable long id) {

    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> putPlayers(@PathVariable long id, @RequestBody PlayerDTO newPlayer) {

    }

    // ------------------------------- Match CRUD operations
    // --------------------------------------------
    @GetMapping("/matches")
    public ResponseEntity<Page<Match>> getMatches() {
    }

    @PostMapping("/matches")
    public ResponseEntity<Match> postMatches(@RequestBody MatchDTO match) {

    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Match> deleteMatches(@PathVariable long id) {

    }

    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> putMatches(@PathVariable long id, @RequestBody MatchDTO newMatch) {

    }

}
