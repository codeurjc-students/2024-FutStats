package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.errors.ElementNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class Playercontroller {

    @Autowired
    RestService restService;

    // ------------------------------- Player CRUD operations

    @GetMapping("/")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(restService.findAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Player player = restService.findPlayerById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));
        return ResponseEntity.ok(player);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name) {
        Player player = restService.findPlayerByName(name)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nobre"));

        return ResponseEntity.ok(player);
    }

    @GetMapping("/{playerId}/league")
    ResponseEntity<League> getLeagueByPlayer(@PathVariable long playerId) {
        Player player = restService.findPlayerById(playerId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        return ResponseEntity.ok(player.getLeague());
    }

    @GetMapping("/{playerId}/team")
    ResponseEntity<Team> getTeamByPlayer(@PathVariable long playerId) {
        Player player = restService.findPlayerById(playerId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        return ResponseEntity.ok(player.getTeam());
    }

    @GetMapping("{playerId}/playersMatches")
    public ResponseEntity<List<PlayerMatch>> getPlayerMatches(@PathVariable long playerId) {
        Player player = restService.findPlayerById(playerId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));
    
        return ResponseEntity.ok(restService.findAllPlayerMatchesByPlayer(playerId));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/")
    public ResponseEntity<Player> postPlayers(@RequestBody PlayerDTO player) {

        Player newPlayer = new Player(player);

        League league = restService.findLeagueByName(player.getLeague())
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        Team team = restService.findTeamByName(player.getTeam())
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        restService.createPlayer(newPlayer, league, team);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayers(HttpServletRequest request, @PathVariable long id) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        Player player = restService.findPlayerById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        restService.deletePlayer(player);

        return ResponseEntity.ok(player);
        
        // if the player ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> putPlayers(HttpServletRequest request, @PathVariable long id, @RequestBody PlayerDTO playerDto) {
        Player oldPlayer = restService.findPlayerById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        Player newPlayer = new Player(playerDto);

        League league = new League();

        //We have to do this comprobation here because we have to know if the league exists
        if(playerDto.getLeague() == null){
            league = oldPlayer.getLeague();
        }else{
            league = restService.findLeagueByName(playerDto.getLeague())
                    .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));
        }

        Team team = new Team();

        //We have to do this comprobation here because we have to know if the team exists
        if(playerDto.getTeam() == null){
            team = oldPlayer.getTeam();
        }else{
            team = restService.findTeamByName(playerDto.getLeague())
                    .orElseThrow(() -> new ElementNotFoundException("No existe una equipo con ese nombre"));
        }

        restService.updatePlayer(oldPlayer, newPlayer, playerDto, league, team);
        
        return ResponseEntity.ok(newPlayer);
        
        // if the player ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }
}