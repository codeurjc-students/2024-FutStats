package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.LeagueDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.services.RestService;

import jakarta.servlet.http.HttpServletRequest;

import com.tfg.futstats.errors.ElementNotFoundException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class LeagueController {

    @Autowired
    RestService restService;

    // ------------------------------- League CRUD operations
    // --------------------------------------------
    @GetMapping("/leagues")
    public ResponseEntity<List<League>> getLeagues() {
        return ResponseEntity.ok(restService.findAllLeagues());
    }

    @GetMapping("/leagues/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable long id) {
        Optional<League> league = restService.findLeagueById(id);

        return ResponseEntity.ok(league.orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id")));
    }

    @GetMapping("/leagues/name/{name}")
    public ResponseEntity<League> getLeagueByName(@PathVariable String name) {
        Optional<League> league = restService.findLeagueByName(name);

        return ResponseEntity.ok(league.orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre")));
    }

    // From this point the only one that can use this methods is the admin, so we
    // have to create security for that

    @PostMapping("/leagues")
    public ResponseEntity<League> postLeagues(HttpServletRequest request, @RequestBody LeagueDTO league) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        League newLeague = new League(league);

        restService.createLeague(newLeague);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newLeague.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLeague);
    }

    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<League> deleteLeagues(HttpServletRequest request, @PathVariable long id) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        League league = restService.findLeagueById(id).orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        restService.deleteLeague(league);
        return ResponseEntity.ok(league);

        //if the league ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/leagues/{id}")
    public ResponseEntity<League> putLeagues(HttpServletRequest request, @PathVariable long id, @RequestBody LeagueDTO newLeague) {

        //We don`t need this because is redundant, is already controlled in SecurityConfig
        /*if (!request.isUserInRole("admin")) {
            throw new ForbiddenAccessException("No tiene permiso para acceder a esta página");
        }*/

        League league = restService.findLeagueById(id).orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        League modLeague = new League(newLeague);

        modLeague.setId(id);
        restService.updateLeague(id, modLeague);
        return ResponseEntity.ok(league);

        //if the league ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

}
