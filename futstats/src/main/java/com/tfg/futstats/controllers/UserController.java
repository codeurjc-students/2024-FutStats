package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired  
    RestService restService;

    @Autowired
    UserService userService;

    //We want that registered users can see their saved leagues
    @GetMapping("users/{id}/leagues")
    public ResponseEntity<Page<League>> getUserLeagues(@RequestParam long id) 
    {
        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).orElseThrow()));
    }

    //We want that registered users can see their saved teams
    @GetMapping("users/{id}/teams")
    public ResponseEntity<Page<Team>> getUserTeams(@RequestParam long id) 
    {
        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).orElseThrow()));
    }

    //We want that registered users can see their saved teams
    @GetMapping("users/{id}/players")
    public ResponseEntity<Page<Player>> getUserPlayers(@RequestParam long id) 
    {
        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).orElseThrow()));
    }
}
