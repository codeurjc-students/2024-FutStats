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

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.errors.ElementNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {

    @Autowired
    RestService restService;

    // ------------------------------- League CRUD operations

    @GetMapping("/")
    public ResponseEntity<List<LeagueDTO>> getLeagues() {
        List<LeagueDTO> leagues = restService.findAllLeagues();
        return ResponseEntity.ok(leagues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable long id) {
        League league = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        LeagueDTO leagueDto = new LeagueDTO(league);

        return ResponseEntity.ok(leagueDto);

        // if the league ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @GetMapping("/name/{leagueName}")
    public ResponseEntity<LeagueDTO> getLeagueByName(@PathVariable String leagueName) {
        League league = restService.findLeagueByName(leagueName)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        LeagueDTO leagueDto = new LeagueDTO(league);

        return ResponseEntity.ok(leagueDto);

        // if the league ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @GetMapping("/{leagueId}/teams")
    public ResponseEntity<List<TeamResponseDTO>> getTeams(@PathVariable long leagueId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findTeamsByLeague(league));
    }

    @GetMapping("name/{leagueName}/teams")
    public ResponseEntity<List<TeamResponseDTO>> getTeamsByName(@PathVariable String leagueName) {
        League league = restService.findLeagueByName(leagueName)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findTeamsByLeague(league));
    }

    @GetMapping("/{leagueId}/matches")
    public ResponseEntity<List<MatchDTO>> getMatchesByLeague(@PathVariable long leagueId) {
        League league = restService.findLeagueById(leagueId)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        return ResponseEntity.ok(restService.findMatchesByLeague(league));
    }

    // From this point the only one that can use this methods is the admin, so we
    // have to create security for that

    @PostMapping("/")
    public ResponseEntity<League> postLeagues(HttpServletRequest request, @RequestBody LeagueDTO league) {
        League newLeague = new League(league);

        restService.createLeague(newLeague);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newLeague.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLeague);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<League> deleteLeagues(HttpServletRequest request, @PathVariable long id) {
        League league = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        restService.deleteLeague(league);

        return ResponseEntity.ok(league);

        // if the league ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> putLeagues(HttpServletRequest request, @PathVariable long id,
            @RequestBody LeagueDTO leagueDto) {
        League oldLeague = restService.findLeagueById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

        League newLeague = new League(leagueDto);

        newLeague.setId(oldLeague.getId());

        restService.updateLeague(newLeague, oldLeague, leagueDto);

        LeagueDTO newLeagueDto = new LeagueDTO(newLeague);

        return ResponseEntity.ok(newLeagueDto);

        // if the league ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }
}
