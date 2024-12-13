package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.tfg.futstats.controllers.dtos.user.UserDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    RestService restService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        return ResponseEntity.ok(userService.findAllUsers());

    }

    @GetMapping("/me")
    public ResponseEntity<User> me(HttpServletRequest request) {
        User user = userService.findUserByName(request.getUserPrincipal().getName())
                .orElseThrow(()-> new ElementNotFoundException("No esta registrado"));
        return ResponseEntity.ok(user);
		
    }

    @PostMapping("/")
    public ResponseEntity<User> postUser(HttpServletRequest request, @RequestBody UserDTO user) {
        if (request.getUserPrincipal().getName() != null) {
            return ResponseEntity.badRequest().build();
        }

        userService.createUser(user.getName(), user.getPassword());

        return ResponseEntity.ok(new User(user.getName(), user.getPassword()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(HttpServletRequest request, @PathVariable long id,@RequestBody UserDTO newUser) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

        User modUser = new User(newUser);

        userService.updateUser(id, modUser);

        return ResponseEntity.ok(user);

        // if the user ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(HttpServletRequest request, @PathVariable long id) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserById(id).orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

        userService.deleteUser(user);

        return ResponseEntity.ok(user);
        
        // if the user ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    // We want that registered users can see their saved leagues
    @GetMapping("/{id}/leagues")
    public ResponseEntity<List<League>> getUserLeagues(HttpServletRequest request, @PathVariable long id) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        // We can use directly the method .get() because we have already proved that the
        // user exists
        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get()));
    }

    @PutMapping("/{id}/leagues/{leagueId}")
    public ResponseEntity<List<League>> addUserLeagues(HttpServletRequest request, @PathVariable long id, @PathVariable long leagueId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        League league = restService.findLeagueById(leagueId)
            .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        userService.addUserLeague(id, league);

        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get()));
    }

    @DeleteMapping("/{id}/leagues/{leaguesId}")
    public ResponseEntity<List<League>> deleteUserLeagues(HttpServletRequest request, @PathVariable long id, @PathVariable long leagueId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        userService.deleteUserLeague(id, league);

        return ResponseEntity.ok(restService.findLeaguesByUser(userService.findUserById(id).get()));
        
        // if the league ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    // We want that registered users can see their saved teams
    @GetMapping("/{id}/teams")
    public ResponseEntity<List<Team>> getUserTeams(HttpServletRequest request, @PathVariable long id) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        // We can use directly the method .get() because we have already proved that the
        // user exists
        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get()));
    }

    @PutMapping("/{id}/teams/{teamId}")
    public ResponseEntity<List<Team>> addUserTeams(HttpServletRequest request, @PathVariable long id, @PathVariable long teamId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        userService.addUserTeam(id, team);

        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get()));

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @DeleteMapping("/{id}/teams/{teamId}")
    public ResponseEntity<List<Team>> deleteUserTeams(HttpServletRequest request, @PathVariable long id, @PathVariable long teamId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        userService.deleteUserTeam(id, team);

        return ResponseEntity.ok(restService.findTeamsByUser(userService.findUserById(id).get()));

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    // We want that registered users can see their saved players
    @GetMapping("/{id}/players")
    public ResponseEntity<List<Player>> getUserPlayers(HttpServletRequest request, @PathVariable long id) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        // We can use directly the method .get() because we have already proved that the
        // user exists
        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get()));
    }

    @PutMapping("/{id}/players/{playerId}")
    public ResponseEntity<List<Player>> addUserPlayers(HttpServletRequest request, @PathVariable long id, @PathVariable long playerId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        Player player = restService.findPlayerById(playerId)
                .orElseThrow(() -> new ElementNotFoundException("no existe un jugador con ese id"));

        userService.addUserPlayer(id, player);

        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get()));

        /// if the player ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @DeleteMapping("/{id}/players/{playerId}")
    public ResponseEntity<List<Player>> deleteUserPlayers(HttpServletRequest request, @PathVariable long id, @PathVariable long playerId) {

        if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id")).getName())) {
            return ResponseEntity.badRequest().build();
        }

        Player player = restService.findPlayerById(playerId)
                .orElseThrow(() -> new ElementNotFoundException("no existe un jugador con ese id"));

        userService.deleteUserPlayer(id, player);

        return ResponseEntity.ok(restService.findPlayersByUser(userService.findUserById(id).get()));

        // if the player ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }
}
