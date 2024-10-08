package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.UserDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(HttpServletRequest request, Pageable pageable) {

        if (!request.isUserInRole("admin")) {

            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok(userService.findAllUsers(PageRequest.of(pageable.getPageNumber(), 5)));

    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(HttpServletRequest request, @RequestBody UserDTO user) {

        if (!request.isUserInRole("admin")) {

            return ResponseEntity.badRequest().build();

        }

        userService.createUser(user.getName(), user.getPassword());

        return ResponseEntity.ok(new User(user.getName(), user.getPassword()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> postUser(HttpServletRequest request, @PathVariable long id, @RequestBody UserDTO newUser) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {

            return ResponseEntity.badRequest().build();

        }

        Optional<User> user = userService.findUserById(id);

        User modUser = new User(newUser);

        if (user.isPresent()){
            userService.updateUser(id, modUser);
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> postUser(HttpServletRequest request, @PathVariable long id) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {

            return ResponseEntity.badRequest().build();

        }

        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(user.get());
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }



    // We want that registered users can see their saved leagues
    @GetMapping("users/{id}/leagues")
    public ResponseEntity<Page<League>> getUserLeagues(HttpServletRequest request, @PathVariable long id, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        //We can use directly the method .get() because we have already proved that the user exists
        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }

    @PutMapping("users/{id}/leagues/{leagueId}")
    public ResponseEntity<Page<League>> addUserLeagues(HttpServletRequest request, @PathVariable long id, @PathVariable long leagueId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<League> league = restService.findLeagueById(leagueId);

        if (league.isPresent()) {
            userService.addUserLeague(id,league.get());
            return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("users/{id}/leagues/{leaguesId}")
    public ResponseEntity<Page<League>> deleteUserLeagues(HttpServletRequest request, @PathVariable long id, @PathVariable long leagueId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<League> league = restService.findLeagueById(leagueId);

        if (league.isPresent()) {
            userService.deleteUserLeague(id,league.get());
            return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();

    }

    // We want that registered users can see their saved teams
    @GetMapping("users/{id}/teams")
    public ResponseEntity<Page<Team>> getUserTeams(HttpServletRequest request, @PathVariable long id, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        //We can use directly the method .get() because we have already proved that the user exists
        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }

    @PutMapping("users/{id}/teams/{teamId}")
    public ResponseEntity<Page<Team>> addUserTeams(HttpServletRequest request, @PathVariable long id, @PathVariable long teamId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<Team> team = restService.findTeamById(teamId);

        if (team.isPresent()) {
            userService.addUserTeam(id,team.get());
            return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("users/{id}/teams/{teamId}")
    public ResponseEntity<Page<Team>> deleteUserTeams(HttpServletRequest request, @PathVariable long id, @PathVariable long teamId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<Team> team = restService.findTeamById(teamId);

        if (team.isPresent()) {
            userService.deleteUserTeam(id,team.get());
            return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();

    }

    // We want that registered users can see their saved teams
    @GetMapping("users/{id}/players")
    public ResponseEntity<Page<Player>> getUserPlayers(HttpServletRequest request, @PathVariable long id, Pageable pageable) {

        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        //We can use directly the method .get() because we have already proved that the user exists
        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
    }

    @PutMapping("users/{id}/players/{playerId}")
    public ResponseEntity<Page<Player>> addUserPlayers(HttpServletRequest request, @PathVariable long id, @PathVariable long playerId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<Player> player = restService.findPlayerById(playerId);

        if (player.isPresent()) {
            userService.addUserPlayer(id,player.get());
            return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("users/{id}/players/{playerId}")
    public ResponseEntity<Page<Player>> deleteUserPlayers(HttpServletRequest request, @PathVariable long id, @PathVariable long playerId, Pageable pageable) {
        if(!request.getUserPrincipal().getName().equals(userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<Player> player = restService.findPlayerById(playerId);

        if (player.isPresent()) {
            userService.deleteUserPlayer(id,player.get());
            return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get(),PageRequest.of(pageable.getPageNumber(),10)));
        }

        return ResponseEntity.notFound().build();

    }
}
