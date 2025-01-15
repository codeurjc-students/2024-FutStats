package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.PlayerMatch;
import com.tfg.futstats.services.RestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/playerMatches")
public class PlayerMatchController {

    @Autowired
    RestService restService;

    // ------------------------------- PlayerMatch CRUD operations
    // --------------------------------------------

    @Operation(summary = "Get playerMatch by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found playerMatch", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatchDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "playerMatch not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlayerMatchDTO> getPlayerMatchById(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        PlayerMatchDTO playerMatchDto = new PlayerMatchDTO(playerMatch);

        return ResponseEntity.ok(playerMatchDto);

        // if the player ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get goals of a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the goals of a player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "playerMatch not found", content = @Content)
    })
    @GetMapping("/goals/{playerId}")
    public List<Map<String, Object>> getGoalsPerMatch(@PathVariable long playerId) {
        List<PlayerMatchDTO> playerMatches = restService.findAllPlayerMatchesByPlayer(playerId);

        return playerMatches.stream().map(playerMatch -> {
            Map<String, Object> map = new HashMap<>();
            map.put("matchName", playerMatch.getMatchName());
            map.put("goals", playerMatch.getGoals());
            return map;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "Get player of a playerMatch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found playerMatch player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "playerMatch not found", content = @Content)
    })
    @GetMapping("/{id}/player")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        PlayerDTO playerDto = new PlayerDTO(playerMatch.getPlayer());

        return ResponseEntity.ok(playerDto);

        // if the player ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get match of a playerMatch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found playerMatch match", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "playerMatch not found", content = @Content)
    })
    @GetMapping("/{id}/match")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable long id) {
        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        MatchDTO matchDto = new MatchDTO(playerMatch.getMatch());

        return ResponseEntity.ok(matchDto);

        // if the player ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Delete a PlayerMatch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PlayerMatch Deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatch.class))

            }),
            @ApiResponse(responseCode = "404", description = "PlayerMatch not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerMatch> deletePlayersMatch(@PathVariable long id) {
        // We don`t need this because is redundant, is already controlled in
        // SecurityConfig

        PlayerMatch playerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        Match match = restService.findMatchById(playerMatch.getMatch().getId())
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese id"));

        Player player = restService.findPlayerByName(playerMatch.getName())
                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nombre"));

        restService.deletePlayerMatch(playerMatch, match, player);

        return ResponseEntity.ok(playerMatch);

        // if the match or the player ins`t found we will never reach this point so it
        // is not necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Update a PlayerMatch")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "PlayerMatch Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "PlayerMatch not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
    @PutMapping("/{id}")
    public ResponseEntity<PlayerMatchDTO> putPlayersMatch(HttpServletRequest request, @PathVariable long id,
            @RequestBody PlayerMatchDTO playerMatchDto) {
        // We don`t need this because is redundant, is already controlled in
        // SecurityConfig

        PlayerMatch oldPlayerMatch = restService.findPlayerMatchById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe el partido de ese jugador"));

        // We have to do this comprobation here because we have to know if the team
        // exists
        Match match = restService.findMatchById(playerMatchDto.getMatch())
                .orElseThrow(() -> new ElementNotFoundException("No existe un partido con ese nombre"));

        Player player = new Player();

        // We have to do this comprobation here because we have to know if the player
        // exists
        if (playerMatchDto.getName() == null) {
            player = oldPlayerMatch.getPlayer();
        } else {
            player = restService.findPlayerByName(playerMatchDto.getName())
                    .orElseThrow(() -> new ElementNotFoundException(
                            "No existe un jugadoer con ese nombre"));
        }

        restService.updatePlayerMatch(oldPlayerMatch, playerMatchDto, match, player);

        PlayerMatchDTO newPlayerMatchDto = new PlayerMatchDTO(oldPlayerMatch);

        return ResponseEntity.ok(newPlayerMatchDto);

        // if the match or the player ins`t found we will never reach this point so it
        // is not necessary
        // to create a not found ResponseEntity
    }
}
