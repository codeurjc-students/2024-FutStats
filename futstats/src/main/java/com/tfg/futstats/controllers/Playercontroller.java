package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.PlayerDTO;
import com.tfg.futstats.controllers.dtos.PlayerMatchDTO;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.services.RestService;

import jakarta.servlet.http.HttpServletRequest;

import com.tfg.futstats.errors.ElementNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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
        Optional<League> league = restService.findLeagueById(leagueId);

        return ResponseEntity.ok(restService.findPlayersByLeague(league.orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/teams/{teamsId}/players")
    public ResponseEntity<Page<Player>> getPlayersByTeam(@PathVariable long teamId, Pageable pageable) {
        Optional<Team> team = restService.findTeamById(teamId);

        return ResponseEntity.ok(restService.findPlayersByTeam(team.orElseThrow(() -> new ElementNotFoundException("no existe un equipo con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/leagues/{leaguesId}/teams/{teamId}players")
    public ResponseEntity<Page<Player>> getPlayers(@PathVariable long leagueId, @PathVariable long teamId, Pageable pageable) {
        Optional<Team> team = restService.findTeamById(teamId);

        Optional<League> league = restService.findLeagueById(leagueId);

        league.orElseThrow(() -> new ElementNotFoundException("no existe una liga con ese id"));

        return ResponseEntity.ok(restService.findPlayersByTeam(team.orElseThrow(() -> new ElementNotFoundException("no existe un equipo con ese id")),PageRequest.of(pageable.getPageNumber(), 5)));
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Optional<Player> player = restService.findPlayerById(id);

        return ResponseEntity.ok(player.orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id")));
    }

    @GetMapping("/player/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name) {
        Optional<Player> player = restService.findPlayerByName(name);

        return ResponseEntity.ok(player.orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nobre")));
    }

    @GetMapping("/playersMatch/{id}")
    public ResponseEntity<PlayerMatch> getPlayerMatch(@PathVariable long id) {
        Optional<PlayerMatch> playerMatch = restService.findPlayerMatchById(id);

        return ResponseEntity.ok(playerMatch.orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador")));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/players")
    public ResponseEntity<Player> postPlayers(HttpServletRequest request, @RequestBody PlayerDTO player) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Player newPlayer = new Player(player);

        League league = restService.findLeagueByName(player.getLeague()).orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        Team team = restService.findTeamByName(player.getTeam()).orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        league.setPlayer(newPlayer);
        team.setPlayer(newPlayer);

        newPlayer.setLeague(league);
        newPlayer.setTeam(team);

        restService.createPlayer(newPlayer);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayer);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayers(HttpServletRequest request, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Player player = restService.findPlayerById(id).orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        restService.deletePlayer(player);
        return ResponseEntity.ok(player);
        
        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<Player> putPlayers(HttpServletRequest request, @PathVariable long id, @RequestBody PlayerDTO newPlayer) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        Player player = restService.findPlayerById(id).orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

        Player modPlayer = new Player(newPlayer);

        modPlayer.setId(id);
        restService.updatePlayer(id, modPlayer);
        return ResponseEntity.ok(player);
        
        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PostMapping("/matches/{id}/playersMatch")
    public ResponseEntity<PlayerMatch> postPlayersMatch(HttpServletRequest request, @RequestBody PlayerMatchDTO playerMatch, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        PlayerMatch newPlayerMatch = new PlayerMatch(playerMatch);

        Match match = restService.findMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));
        Player player = restService.findPlayerByName(playerMatch.getPlayer()).orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nombre"));

        newPlayerMatch.setMatch(match);
        newPlayerMatch.setPlayer(player);

        player.setPlayerMatch(newPlayerMatch);
        match.setPlayerMatch(newPlayerMatch);

        restService.createPlayerMatch(newPlayerMatch);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayerMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayerMatch);
    }

    @DeleteMapping("/matches/{matchId}/playersMatch/{id}")
    public ResponseEntity<PlayerMatch> deletePlayersMatch(HttpServletRequest request, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        PlayerMatch playerMatch = restService.findPlayerMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        restService.deletePlayerMatch(playerMatch,id);
        return ResponseEntity.ok(playerMatch);

        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/matches/{matchId}/playersMatch/{id}")
    public ResponseEntity<PlayerMatch> putPlayersMatch(HttpServletRequest request, @PathVariable long id, @RequestBody PlayerMatchDTO newPlayerMatch) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        PlayerMatch playerMatch = restService.findPlayerMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        PlayerMatch modPlayerMatch = new PlayerMatch(newPlayerMatch);

        modPlayerMatch.setId(id);
        modPlayerMatch.setMatch(playerMatch.getMatch());
        modPlayerMatch.setPlayer(playerMatch.getPlayer());

        restService.updatePlayerMatch(id, modPlayerMatch);
        return ResponseEntity.ok(playerMatch);

        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }
}
