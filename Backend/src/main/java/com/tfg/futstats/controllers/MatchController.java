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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

        @Autowired
        RestService restService;

        // ------------------------------- Match CRUD operations
        @Operation(summary = "Get all the matches")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found matches", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))
                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/")
        public ResponseEntity<List<MatchDTO>> getMatches() {
                return ResponseEntity.ok(restService.findAllMatches());
        }
        
        @Operation(summary = "Get match by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<MatchDTO> getMatch(@PathVariable long id) {
                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                MatchDTO matchDto = new MatchDTO(match);

                return ResponseEntity.ok(matchDto);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get match by name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("/name/{name}")
        public ResponseEntity<MatchDTO> getMatch(@PathVariable String name) {
                Match match = restService.findMatchByName(name)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                MatchDTO matchDto = new MatchDTO(match);

                return ResponseEntity.ok(matchDto);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get league of a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match league", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("/{matchId}/league")
        ResponseEntity<LeagueDTO> getLeague(@PathVariable long matchId) {
                Match match = restService.findMatchById(matchId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                LeagueDTO league = new LeagueDTO(match.getLeague());

                return ResponseEntity.ok(league);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get team1 of a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match team1", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("/{id}/team1")
        public ResponseEntity<TeamResponseDTO> getTeam1(@PathVariable long id) {
                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                TeamResponseDTO team = new TeamResponseDTO(match.getTeam1());

                return ResponseEntity.ok(team);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get team2 of a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match team2", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("/{id}/team2")
        public ResponseEntity<TeamResponseDTO> getTeam2(@PathVariable long id) {
                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                TeamResponseDTO team = new TeamResponseDTO(match.getTeam2());

                return ResponseEntity.ok(team);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get playerMatches of a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found match playerMatches", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/{id}/playerMatches")
        public ResponseEntity<List<PlayerMatchDTO>> getPlayerMatch(@PathVariable long id) {
                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

                return ResponseEntity.ok(restService.findAllPlayerMatchesByMatch(id));

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        // From this point the only one that can use this methods is the admin so we
        // have to create security for that

        @Operation(summary = "Create a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Match Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/")
        public ResponseEntity<MatchDTO> postMatches(@RequestBody MatchDTO matchDto) {
                // We don`t need this because it is already controlled in SecurityConfig

                Match newMatch = new Match(matchDto);

                League league = restService.findLeagueByName(matchDto.getLeague())
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

                Team team1 = restService.findTeamByName(matchDto.getTeam1())
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

                Team team2 = restService.findTeamByName(matchDto.getTeam2())
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

                restService.createMatch(newMatch, league, team1, team2);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(newMatch.getId())
                                .toUri();

                MatchDTO newMatchDto = new MatchDTO(newMatch);

                return ResponseEntity.created(location).body(newMatchDto);
        }

        @Operation(summary = "Delete a match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Match Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<MatchDTO> deleteMatches(@PathVariable long id) {
                // We don`t need this because is redundant, is already controlled in
                // SecurityConfig

                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

                MatchDTO matchDto = new MatchDTO(match);

                restService.deleteMatch(match);

                return ResponseEntity.ok(matchDto);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Update a Match")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Match Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<MatchDTO> putMatches(@PathVariable long id,
                        @RequestBody MatchDTO matchDto) {
                // We don`t need this because is redundant, is already controlled in
                // SecurityConfig

                Match oldMatch = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

                League league = new League();

                // We have to do this comprobation here because we have to know if the league
                // exists
                if (matchDto.getLeague() == null) {
                        league = oldMatch.getLeague();
                } else {
                        league = restService.findLeagueByName(matchDto.getLeague())
                                        .orElseThrow(() -> new ElementNotFoundException(
                                                        "No existe una liga con ese nombre"));
                }

                Team team1 = new Team();

                // We have to do this comprobation here because we have to know if the team
                // exists
                if (matchDto.getTeam1() == null) {
                        team1 = oldMatch.getTeam1();
                } else {
                        team1 = restService.findTeamByName(matchDto.getTeam1())
                                        .orElseThrow(() -> new ElementNotFoundException(
                                                        "No existe una equipo con ese nombre"));
                }

                Team team2 = new Team();

                // We have to do this comprobation here because we have to know if the team
                // exists
                if (matchDto.getTeam2() == null) {
                        team2 = oldMatch.getTeam2();
                } else {
                        team2 = restService.findTeamByName(matchDto.getTeam2())
                                        .orElseThrow(() -> new ElementNotFoundException(
                                                        "No existe una equipo con ese nombre"));
                }

                
                restService.updateMatch(oldMatch, matchDto, league, team1, team2);

                MatchDTO newMatchDto = new MatchDTO(oldMatch);

                return ResponseEntity.ok(newMatchDto);

                // if the match ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Create a playerMatch")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "playerMatch Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/{id}/playerMatches")
        public ResponseEntity<PlayerMatch> postPlayersMatch(HttpServletRequest request,
                        @RequestBody PlayerMatchDTO playerMatchDto, @PathVariable long id) {
                // We don`t need this because is redundant, is already controlled in
                // SecurityConfig

                PlayerMatch newPlayerMatch = new PlayerMatch(playerMatchDto);

                Match match = restService.findMatchById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

                Player player = restService.findPlayerByName(playerMatchDto.getName())
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nombre"));

                restService.createPlayerMatch(newPlayerMatch, match, player);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(newPlayerMatch.getId())
                                .toUri();

                return ResponseEntity.created(location).body(newPlayerMatch);

                // if the match or the player ins`t found we will never reach this point so it
                // is not necessary
                // to create a not found ResponseEntity
        }
}
