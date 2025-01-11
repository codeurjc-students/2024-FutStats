package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerResponseDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.errors.ElementNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class Playercontroller {

        @Autowired
        RestService restService;

        // ------------------------------- Player CRUD operations

        @Operation(summary = "Get player by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "player not found", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<PlayerResponseDTO> getPlayer(@PathVariable long id) {
                Player player = restService.findPlayerById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                PlayerResponseDTO playerDto = new PlayerResponseDTO(player);

                return ResponseEntity.ok(playerDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get player by name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "player not found", content = @Content)
        })
        @GetMapping("/name/{name}")
        public ResponseEntity<PlayerResponseDTO> getPlayerByName(@PathVariable String name) {
                Player player = restService.findPlayerByName(name)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese nobre"));

                PlayerResponseDTO playerDto = new PlayerResponseDTO(player);

                return ResponseEntity.ok(playerDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get player image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player image", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Blob.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "player not found", content = @Content)
        })
        @GetMapping("/{id}/image")
        public ResponseEntity<Blob> getImage(HttpServletRequest request, @PathVariable long id) {
                Player player = restService.findPlayerById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));

                return ResponseEntity.ok(player.getImageFile());
        }

        @Operation(summary = "Get league of a player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player league", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "player not found", content = @Content)
        })
        @GetMapping("/{playerId}/league")
        ResponseEntity<LeagueDTO> getLeagueByPlayer(@PathVariable long playerId) {
                Player player = restService.findPlayerById(playerId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                LeagueDTO leagueDto = new LeagueDTO(player.getLeague());

                return ResponseEntity.ok(leagueDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get team of a player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player team", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "player not found", content = @Content)
        })
        @GetMapping("/{playerId}/team")
        ResponseEntity<TeamResponseDTO> getTeamByPlayer(@PathVariable long playerId) {
                Player player = restService.findPlayerById(playerId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                TeamResponseDTO teamDto = new TeamResponseDTO(player.getTeam());

                return ResponseEntity.ok(teamDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get playerMatch of a plyer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found player playerMatches", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerMatchDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "match not found", content = @Content)
        })
        @GetMapping("{playerId}/playerMatches")
        public ResponseEntity<List<PlayerMatchDTO>> getPlayerMatches(@PathVariable long playerId) {
                Player player = restService.findPlayerById(playerId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                return ResponseEntity.ok(restService.findAllPlayerMatchesByPlayer(playerId));

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        // From this point the only one that can use this methods is the admin so we
        // have to create security for that

        @Operation(summary = "Create a player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Player Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/")
        public ResponseEntity<PlayerResponseDTO> postPlayers(@RequestBody PlayerDTO playerDto) {
                // We don`t need this because it is already controlled in SecurityConfig

                Player newPlayer = new Player(playerDto);

                League league = restService.findLeagueByName(playerDto.getLeague())
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

                Team team = restService.findTeamByName(playerDto.getTeam())
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

                restService.createPlayer(newPlayer, league, team);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(newPlayer.getId())
                                .toUri();

                PlayerResponseDTO newPlayerDto = new PlayerResponseDTO(newPlayer);

                return ResponseEntity.created(location).body(newPlayerDto);

                // if the team or the league ins`t found we will never reach this point so it is
                // not necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Create a League image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "League Image Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/{id}/image")
        public ResponseEntity<PlayerResponseDTO> uploadImage(@PathVariable long id,
                        @RequestParam MultipartFile imageFile)
                        throws IOException {

                Player player = restService.findPlayerById(id).orElseThrow();

                player.setImage(true);
                player.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                restService.savePlayer(player);

                PlayerResponseDTO playerDto = new PlayerResponseDTO(player);

                return ResponseEntity.ok(playerDto);
        }

        @Operation(summary = "Delete a Player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Player Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<PlayerDTO> deletePlayers(HttpServletRequest request, @PathVariable long id) {
                // We don`t need this because is redundant, is already controlled in
                // SecurityConfig

                Player player = restService.findPlayerById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                PlayerDTO playerDto = new PlayerDTO(player);

                restService.deletePlayer(player);

                return ResponseEntity.ok(playerDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete a Player image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Player Image Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
        })
        @DeleteMapping("/{id}/image")
        public ResponseEntity<PlayerResponseDTO> deleteImage(@PathVariable long id) {

                Player player = restService.findPlayerById(id).orElseThrow();

                player.setImageFile(null);
                player.setImage(false);

                restService.savePlayer(player);

                PlayerResponseDTO playerDto = new PlayerResponseDTO(player);

                return ResponseEntity.ok(playerDto);
        }

        @Operation(summary = "Update a Player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Player Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<PlayerResponseDTO> putPlayers(HttpServletRequest request, @PathVariable long id,
                        @RequestBody PlayerDTO playerDto) {
                // We don`t need this because it is already controlled in SecurityConfig

                Player oldPlayer = restService.findPlayerById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un jugador con ese id"));

                League league = new League();

                // We have to do this comprobation here because we have to know if the league
                // exists
                if (playerDto.getLeague() == null) {
                        league = oldPlayer.getLeague();
                } else {
                        league = restService.findLeagueByName(playerDto.getLeague())
                                        .orElseThrow(() -> new ElementNotFoundException(
                                                        "No existe una liga con ese nombre"));
                }

                Team team = new Team();

                // We have to do this comprobation here because we have to know if the team
                // exists
                if (playerDto.getTeam() == null) {
                        team = oldPlayer.getTeam();
                } else {
                        team = restService.findTeamByName(playerDto.getLeague())
                                        .orElseThrow(() -> new ElementNotFoundException(
                                                        "No existe una equipo con ese nombre"));
                }

                restService.updatePlayer(oldPlayer, playerDto, league, team);

                PlayerResponseDTO newPlayerDto = new PlayerResponseDTO(oldPlayer);

                return ResponseEntity.ok(newPlayerDto);

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }
}