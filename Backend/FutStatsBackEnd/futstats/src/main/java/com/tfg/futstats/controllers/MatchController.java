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
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

     @Autowired
    RestService restService;

    // ------------------------------- Match CRUD operations
    // --------------------------------------------
    @GetMapping("/")
    public ResponseEntity<List<MatchDTO>> getMatches() {
        return ResponseEntity.ok(restService.findAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable long id) {
        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        MatchDTO matchDto = new MatchDTO(match);

        return ResponseEntity.ok(matchDto);
    }

    @GetMapping("/{matchId}/league")
    ResponseEntity<LeagueDTO> getLeague(@PathVariable long matchId) {
        Match match = restService.findMatchById(matchId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        LeagueDTO league = new LeagueDTO(match.getLeague());

        return ResponseEntity.ok(league);
    }

    @GetMapping("/{id}/team1")
    public ResponseEntity<TeamResponseDTO> getTeam1(@PathVariable long id) {
        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        TeamResponseDTO team = new TeamResponseDTO(match.getTeam1());

        return ResponseEntity.ok(team);
    }

    @GetMapping("/{id}/team2")
    public ResponseEntity<TeamResponseDTO> getTeam2(@PathVariable long id) {
        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        TeamResponseDTO team = new TeamResponseDTO(match.getTeam2());
        
        return ResponseEntity.ok(team);
    }

    @GetMapping("/{id}/playersMatches")
    public ResponseEntity<List<PlayerMatchDTO>> getPlayerMatch(@PathVariable long id) {
        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        return ResponseEntity.ok(restService.findAllPlayerMatchesByMatch(id));
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @PostMapping("/")
    public ResponseEntity<Match> postMatches(HttpServletRequest request, @RequestBody MatchDTO matchDto) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        Match newMatch = new Match(matchDto);

        League league = restService.findLeagueByName(matchDto.getLeague())
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        Team team1 = restService.findTeamByName(matchDto.getTeam1())
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        Team team2 = restService.findTeamByName(matchDto.getTeam2())
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        restService.createMatch(newMatch, league, team1, team2);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Match> deleteMatches(HttpServletRequest request, @PathVariable long id) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig
   
        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        restService.deleteMatch(match);

        return ResponseEntity.ok(match);
        
        // if the match ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> putMatches(HttpServletRequest request, @PathVariable long id, @RequestBody MatchDTO matchDto) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        Match oldMatch = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        Match newMatch = new Match(matchDto);

        League league = new League();

        //We have to do this comprobation here because we have to know if the league exists
        if(matchDto.getLeague() == null){
                league = oldMatch.getLeague();
        }else{
                league = restService.findLeagueByName(matchDto.getLeague())
                        .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));
        }
    
        Team team1 = new Team();
    
        //We have to do this comprobation here because we have to know if the team exists
        if(matchDto.getTeam1() == null){
                team1 = oldMatch.getTeam1();
        }else{
                team1 = restService.findTeamByName(matchDto.getTeam1())
                        .orElseThrow(() -> new ElementNotFoundException("No existe una equipo con ese nombre"));
        }

        Team team2 = new Team();
    
        //We have to do this comprobation here because we have to know if the team exists
        if(matchDto.getTeam2() == null){
                team2 = oldMatch.getTeam2();
        }else{
                team2 = restService.findTeamByName(matchDto.getTeam2())
                        .orElseThrow(() -> new ElementNotFoundException("No existe una equipo con ese nombre"));
        }

        restService.updateMatch(oldMatch, newMatch, matchDto, league, team1, team2);

        MatchDTO newMatchDto = new MatchDTO(newMatch);

        return ResponseEntity.ok(newMatchDto);

        // if the match ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @PostMapping("/{id}/playersMatch")
    public ResponseEntity<PlayerMatch> postPlayersMatch(HttpServletRequest request, @RequestBody PlayerMatchDTO playerMatchDto, @PathVariable long id) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        PlayerMatch newPlayerMatch = new PlayerMatch(playerMatchDto);

        Match match = restService.findMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        Player player = restService.findPlayerByName(playerMatchDto.getName())
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nombre"));

        restService.createPlayerMatch(newPlayerMatch, match, player);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayerMatch.getId())
                .toUri();

        return ResponseEntity.created(location).body(newPlayerMatch);
    }

    @DeleteMapping("/{matchId}/playersMatch/{id}")
    public ResponseEntity<PlayerMatch> deletePlayersMatch(HttpServletRequest request, @PathVariable long id) {
        //We don`t need this because is redundant, is already controlled in SecurityConfig

        PlayerMatch playerMatch = restService.findPlayerMatchById(id).orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        restService.deletePlayerMatch(playerMatch);
        return ResponseEntity.ok(playerMatch);

        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }

    @PutMapping("/{matchId}/playersMatch/{id}")
    public ResponseEntity<PlayerMatchDTO> putPlayersMatch(HttpServletRequest request, @PathVariable long id, @RequestBody PlayerMatchDTO playerMatchDto) {
        PlayerMatch oldPlayerMatch = restService.findPlayerMatchById(id)
                        .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        PlayerMatch newPlayerMatch = new PlayerMatch(playerMatchDto);

        //We have to do this comprobation here because we have to know if the team exists
        Match match = restService.findMatchById(id)
                        .orElseThrow(() -> new ElementNotFoundException("No existe una equipo con ese nombre"));
    
        Player player = new Player();
    
        //We have to do this comprobation here because we have to know if the player exists
        if(playerMatchDto.getName() == null){
                player = oldPlayerMatch.getPlayer();
        }else{
                player = restService.findPlayerByName(playerMatchDto.getName())
                        .orElseThrow(() -> new ElementNotFoundException("No existe un jugadoer con ese nombre"));
        }

        restService.updatePlayerMatch(oldPlayerMatch, newPlayerMatch, playerMatchDto, match, player);

        PlayerMatchDTO newPlayerMatchDto = new PlayerMatchDTO(newPlayerMatch);
        
        return ResponseEntity.ok(newPlayerMatchDto);

        //if the player ins`t found we will never reach this point but for security we will let this here
        //return ResponseEntity.notFound().build();
    }
}
