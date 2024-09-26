package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.PlayerDTO;
import com.tfg.futstats.models.Player;
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
public class Playercontroller {

    @Autowired
    RestService restService;

    // ------------------------------- Player CRUD operations
    // --------------------------------------------
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAllPlayers(Pageable pageable) {
        return ResponseEntity.ok(restService.findAllPlayers(PageRequest.of(pageable.getPageNumber(), 10)));
    }

    @GetMapping("/leagues/{leaguesId}/players")
    public ResponseEntity<Page<Player>> getPlayersByLeague(@PathVariable long leagueId, Pageable pageable) {
        return ResponseEntity.ok(restService.findPlayersByLeague(leagueId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/teams/{teamsId}/players")
    public ResponseEntity<Page<Player>> getPlayersByTeam(@PathVariable long teamId, Pageable pageable) {
        return ResponseEntity.ok(restService.findPlayersByTeam(teamId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId, @PathVariable long teamId, Pageable pageable) {
        return ResponseEntity.ok(restService.findPlayersByTeam(teamId,PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Optional<Player> player = restService.findPlayerById(id);

        return ResponseEntity.ok(player.orElseThrow());
    }

    @GetMapping("/player/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name) {
        Optional<Player> player = restService.findPlayerByName(name);

        return ResponseEntity.ok(player.orElseThrow());
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/players")
    public ResponseEntity<Player> postPlayers(@RequestBody PlayerDTO player) {
        Player newPlayer = new Player(player);

        restService.createPlayer(newPlayer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayer);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayers(@PathVariable long id) {
        Optional<Player> player = restService.findPlayerById(id);
        if (player.isPresent()) {
            restService.deletePlayer(player.get());
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> putPlayers(@PathVariable long id, @RequestBody PlayerDTO newPlayer) {
        Optional<Player> player = restService.findPlayerById(id);

        Player modPlayer = new Player(newPlayer);

        if (player.isPresent()) {
            modPlayer.setId(id);
            restService.updatePlayer(id, modPlayer);
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }
}
