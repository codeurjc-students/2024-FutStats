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
import com.tfg.futstats.controllers.dtos.team.TeamCreationDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.match.MatchDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.services.RestService;

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
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    RestService restService;


    // ------------------------------- Team CRUD operations
    @Operation(summary = "Get team by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeam(@PathVariable long id) {
        Team team = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        TeamResponseDTO teamDto = new TeamResponseDTO(team);

        return ResponseEntity.ok(teamDto);

        // if the team ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get team by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<TeamResponseDTO> getTeamByName(@PathVariable String name) {
        Team team = restService.findTeamByName(name)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese nombre"));

        TeamResponseDTO teamDto = new TeamResponseDTO(team);

        return ResponseEntity.ok(teamDto);

        // if the team ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get team image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team image", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Blob.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/{id}/image")
    public ResponseEntity<Blob> getImage(HttpServletRequest request, @PathVariable long id) {
        Team team = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));

        return ResponseEntity.ok(team.getImageFile());
    }

    @Operation(summary = "Get league of a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team league", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/{teamId}/league")
    ResponseEntity<LeagueDTO> getLeagueByTeam(@PathVariable long teamId) {
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        LeagueDTO league = new LeagueDTO(team.getLeague());

        return ResponseEntity.ok(league);

        // if the team ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get players of a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team players", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/{teamId}/players")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable long teamId) {
        Team team = restService.findTeamById(teamId)
                .orElseThrow(() -> new ElementNotFoundException("no existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findPlayersByTeam(team));

        // if the team ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Get matches of a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found team matches", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatchDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "team not found", content = @Content)
    })
    @GetMapping("/{teamsId}/matches")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable long teamsId) {
        Team team = restService.findTeamById(teamsId)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        return ResponseEntity.ok(restService.findMatchesByTeam(team));

        // if the team ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    // From this point the only one that can use this methods is the admin so we
    // have to create security for that

    @Operation(summary = "Create a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

    })
    @PostMapping("/")
    public ResponseEntity<TeamResponseDTO> postTeams(HttpServletRequest request, @RequestBody TeamCreationDTO teamDto) {
        // We don`t need this because it is already controlled in SecurityConfig

        Team newTeam = new Team(teamDto);

        League league = restService.findLeagueByName(teamDto.getLeague())
                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));

        restService.createTeam(newTeam, league);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTeam.getId())
                .toUri();

        TeamResponseDTO newTeamDto = new TeamResponseDTO(newTeam);

        return ResponseEntity.created(location).body(newTeamDto);

        // if the league ins`t found we will never reach this point so it is not
        // necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Create a Team image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team Image Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

    })
    @PostMapping("/{id}/image")
    public ResponseEntity<TeamResponseDTO> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
            throws IOException {

        Team team = restService.findTeamById(id).orElseThrow();

        team.setImage(true);
        team.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        restService.saveTeam(team);

        TeamResponseDTO teamDto = new TeamResponseDTO(team);

        return ResponseEntity.ok(teamDto);
    }

    @Operation(summary = "Delete a Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team Deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))

            }),
            @ApiResponse(responseCode = "404", description = "Team not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> deleteTeams(HttpServletRequest request, @PathVariable long id) {
        // We don`t need this because it is already controlled in SecurityConfig

        Team team = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        TeamResponseDTO teamDto = new TeamResponseDTO(team);

        restService.deleteTeam(team);

        return ResponseEntity.ok(teamDto);

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }

    @Operation(summary = "Delete a Team image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team Image Deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "Team not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @DeleteMapping("/{id}/image")
    public ResponseEntity<TeamResponseDTO> deleteImage(@PathVariable long id) {

        Team team = restService.findTeamById(id).orElseThrow();

        team.setImageFile(null);
        team.setImage(false);

        restService.saveTeam(team);

        TeamResponseDTO teamDto = new TeamResponseDTO(team);

        return ResponseEntity.ok(teamDto);
    }

    @Operation(summary = "Update a Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "Team not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> putTeams(HttpServletRequest request, @PathVariable long id,
            @RequestBody TeamUpdateDTO teamDto) {
        // We don`t need this because it is already controlled in SecurityConfig

        Team oldTeam = restService.findTeamById(id)
                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

        League league = new League();

        // We have to do this comprobation here because we have to know if the league
        // exists
        if (teamDto.getLeague() == null) {
            league = oldTeam.getLeague();
        } else {
            league = restService.findLeagueByName(teamDto.getLeague())
                    .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese nombre"));
        }

        restService.updateTeam(oldTeam, teamDto, league);

        TeamResponseDTO newTeamDto = new TeamResponseDTO(oldTeam);

        return ResponseEntity.ok(newTeamDto);

        // if the team ins`t found we will never reach this point so it is not necessary
        // to create a not found ResponseEntity
    }
}