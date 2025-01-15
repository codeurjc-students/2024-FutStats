package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;
import com.tfg.futstats.services.RestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/teamMatches")
public class TeamMatchController {

    @Autowired
    RestService restService;

    @Operation(summary = "Get points of a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the points of a team", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TeamMatchDTO.class))

            }),
            @ApiResponse(responseCode = "404", description = "teamMatch not found", content = @Content)
    })
    @GetMapping("/goals/{teamId}")
    public List<Map<String, Object>> getPointsPerMatch(@PathVariable long teamId) {
        List<TeamMatchDTO> teamMatches = restService.findAllTeamMatchesByTeam(teamId);

        return teamMatches.stream().map(teamMatch -> {
            Map<String, Object> map = new HashMap<>();
            map.put("matchName", teamMatch.getMatchName());
            map.put("points", teamMatch.getPoints());
            return map;
        }).collect(Collectors.toList());
    }
}
