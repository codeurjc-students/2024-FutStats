package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.controllers.DTOs.PlayerDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
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
public class Playercontroller {

    @Autowired
    RestService restService;

    // ------------------------------- Player CRUD operations
    // --------------------------------------------
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAllPlayers() {
        return ResponseEntity.ok(restService.findAllPlayers(), PageRequest.of(pageable.getPageNumber(), 10));
    }

    @GetMapping("/leagues/{leaguesId}/players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId) {
        Optional<League> league = leagueService.findLeagueById(leagueId);

        return ResponseEntity.ok(league.orElseThrow().getPlayers());
    }

    @GetMapping("/teams/{teamsId}/players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getPlayers());
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId, @PathVariable long teamId) {
        Optional<Team> team = leagueService.findTeamById(teamId);

        return ResponseEntity.ok(team.orElseThrow().getPlayers());
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Optional<Player> player = leagueService.findPlayerById(id);

        return ResponseEntity.ok(player.orElseThrow());
    }

    @GetMapping("/player/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name) {
        Optional<Player> player = leagueService.findPlayerByNameIgnoreCase(name);

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
        Optional<Player> player = restService.findPlayerById(player);
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
