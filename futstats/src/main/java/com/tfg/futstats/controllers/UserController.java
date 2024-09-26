package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    RestService restService;

    @Autowired
    UserService userService;

    // We want that registered users can see their saved leagues
    @GetMapping("users/{id}/leagues")
    public ResponseEntity<Page<League>> getUserLeagues(@PathVariable long id, Pageable pageable) {
        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }

    // We want that registered users can see their saved teams
    @GetMapping("users/{id}/teams")
    public ResponseEntity<Page<Team>> getUserTeams(@PathVariable long id, Pageable pageable) {
        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }

    // We want that registered users can see their saved teams
    @GetMapping("users/{id}/players")
    public ResponseEntity<Page<Player>> getUserPlayers(@PathVariable long id, Pageable pageable) {
        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }
}
