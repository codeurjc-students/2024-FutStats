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
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.errors.ElementNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.util.List;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {

        @Autowired
        RestService restService;

        // ------------------------------- League CRUD operations

        @Operation(summary = "Get all the leagues")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found leagues", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/")
        public ResponseEntity<List<LeagueDTO>> getLeagues() {
                List<LeagueDTO> leagues = restService.findAllLeagues();
                return ResponseEntity.ok(leagues);
        }

        @Operation(summary = "Get league by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found league", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content)
        })
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

        @Operation(summary = "Get league by name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found league", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content)
        })
        @GetMapping("/name/{leagueName}")
        public ResponseEntity<LeagueDTO> getLeagueByName(@PathVariable String leagueName) {
                League league = restService.findLeagueByName(leagueName)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

                LeagueDTO leagueDto = new LeagueDTO(league);

                return ResponseEntity.ok(leagueDto);
        }

        @Operation(summary = "Get league image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found image", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Blob.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
        })
        @GetMapping("/{id}/image")
        public ResponseEntity<Object> getImage(HttpServletRequest request, @PathVariable long id)throws SQLException {
                League league = restService.findLeagueById(id)
                        .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));

                Resource file = new InputStreamResource(league.getImageFile().getBinaryStream());
 
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
		        .contentLength(league.getImageFile().length()).body(file);
        }

        @Operation(summary = "Get teams of a league")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found league teams", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/{leagueId}/teams")
        public ResponseEntity<List<TeamResponseDTO>> getTeams(@PathVariable long leagueId) {
                League league = restService.findLeagueById(leagueId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                return ResponseEntity.ok(restService.findTeamsByLeague(league));

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get teams of a league by league name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found league teams", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Name", content = @Content)
        })
        @GetMapping("name/{leagueName}/teams")
        public ResponseEntity<List<TeamResponseDTO>> getTeamsByName(@PathVariable String leagueName) {
                League league = restService.findLeagueByName(leagueName)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                return ResponseEntity.ok(restService.findTeamsByLeague(league));

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get matches of a league")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found league matches", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/{leagueId}/matches")
        public ResponseEntity<List<MatchDTO>> getMatchesByLeague(@PathVariable long leagueId) {
                League league = restService.findLeagueById(leagueId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                return ResponseEntity.ok(restService.findMatchesByLeague(league));

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        // From this point the only one that can use this methods is the admin, so we
        // have to create security for that

        @Operation(summary = "Create a League")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "League Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/")
        public ResponseEntity<LeagueDTO> postLeagues(HttpServletRequest request, @RequestBody LeagueDTO leagueDto) {
                // We don`t need security here because it`s already controlled in SecurityConfig

                League newLeague = new League(leagueDto);

                restService.createLeague(newLeague);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(newLeague.getId())
                                .toUri();

                LeagueDTO newLeagueDto = new LeagueDTO(newLeague);

                return ResponseEntity.created(location).body(newLeagueDto);
        }

        @Operation(summary = "Create a League image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "League Image Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/{id}/image")
        public ResponseEntity<LeagueDTO> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
                        throws IOException {

                League league = restService.findLeagueById(id).orElseThrow();

                league.setImage(true);
                league.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                restService.saveLeague(league);

                LeagueDTO leagueDto = new LeagueDTO(league);

                return ResponseEntity.ok(leagueDto);
        }

        @Operation(summary = "Delete a League")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "League Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = League.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<LeagueDTO> deleteLeagues(@PathVariable long id) {
                // We don`t need security here because it`s already controlled in SecurityConfig

                League league = restService.findLeagueById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                LeagueDTO leagueDto = new LeagueDTO(league);

                restService.deleteLeague(league);

                return ResponseEntity.ok(leagueDto);

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete a League image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "League Image Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
        })
        @DeleteMapping("/{id}/image")
        public ResponseEntity<LeagueDTO> deleteImage(@PathVariable long id) {

                League league = restService.findLeagueById(id).orElseThrow();

                league.setImageFile(null);
                league.setImage(false);

                restService.saveLeague(league);

                LeagueDTO leagueDto = new LeagueDTO(league);

                return ResponseEntity.ok(leagueDto);
        }

        @Operation(summary = "Update a League")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "League Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "League not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<LeagueDTO> putLeagues(HttpServletRequest request, @PathVariable long id,
                        @RequestBody LeagueDTO leagueDto) {
                // We don`t need security here because it`s already controlled in SecurityConfig

                League league = restService.findLeagueById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                restService.updateLeague(league, leagueDto);

                LeagueDTO newLeagueDto = new LeagueDTO(league);

                return ResponseEntity.ok(newLeagueDto);

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }
}
