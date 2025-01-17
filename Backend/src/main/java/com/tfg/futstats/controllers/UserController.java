package com.tfg.futstats.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import java.sql.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.tfg.futstats.controllers.dtos.user.UserDTO;
import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.controllers.dtos.user.UserResponseDTO;
import com.tfg.futstats.errors.ElementNotFoundException;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

        @Autowired
        RestService restService;

        @Autowired
        UserService userService;

        @Operation(summary = "Get all the users")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found users", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "204", description = "No content", content = @Content)
        })
        @GetMapping("/")
        public ResponseEntity<List<UserResponseDTO>> getUsers(HttpServletRequest request) {
                return ResponseEntity.ok(userService.findAllUsers());

        }

        @Operation(summary = "Get me")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found me", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "me not found", content = @Content)
        })
        @GetMapping("/me")
        public ResponseEntity<UserResponseDTO> me(HttpServletRequest request) {

                if (request.getUserPrincipal() != null) {
                        User user = userService.findUserByName(request.getUserPrincipal().getName())
                                        .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));
                        if (!request.getUserPrincipal().getName().equals(user.getName())) {
                                return ResponseEntity.badRequest().build();
                        } else {
                                UserResponseDTO userDto = new UserResponseDTO(user);

                                return ResponseEntity.ok(userDto);
                        }
                } else {
                        return ResponseEntity.badRequest().build();
                }
        }

        @Operation(summary = "Get user by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found user", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "user not found", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<UserResponseDTO> getUser(HttpServletRequest request, @PathVariable long id) {
                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                UserResponseDTO userDto = new UserResponseDTO(user);

                return ResponseEntity.ok(userDto);
        }

        @Operation(summary = "Get user image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found user image", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Blob.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "user not found", content = @Content)
        })
        @GetMapping("/{id}/image")
        public ResponseEntity<Blob> getImage(HttpServletRequest request, @PathVariable long id) {
                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No esta registrado"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                return ResponseEntity.ok(user.getImageFile());
        }

        @Operation(summary = "Create an user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/")
        public ResponseEntity<UserResponseDTO> postUser(@RequestBody UserDTO userDto) {

                User user = new User(userDto);
   
                userService.createUser(user);

                UserResponseDTO newUserDto = new UserResponseDTO(user);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(newUserDto.getId())
                                .toUri();

                return ResponseEntity.created(location).body(newUserDto);
        }

        @Operation(summary = "Create an User image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User Image Created", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)

        })
        @PostMapping("/{id}/image")
        public ResponseEntity<UserResponseDTO> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
                        throws IOException {

                User user = userService.findUserById(id).orElseThrow();

                user.setImage(true);
                user.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                userService.save(user);

                UserResponseDTO userDto = new UserResponseDTO(user);

                return ResponseEntity.ok(userDto);
        }

        @Operation(summary = "Update an User")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<UserResponseDTO> putUser(HttpServletRequest request, @PathVariable long id,
                        @RequestBody UserDTO newUser) {

                if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {
                        return ResponseEntity.badRequest().build();
                }

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                userService.updateUser(user, newUser);

                UserResponseDTO userDto = new UserResponseDTO(user);

                return ResponseEntity.ok(userDto);

                // if the user ins`t found we will never reach this point so it is not necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete an User")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "Team not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<User> deleteUser(HttpServletRequest request, @PathVariable long id) {
                // We don`t need this because is redundant, is already controlled in
                // SecurityConfig

                if (!request.getUserPrincipal().getName().equals(userService.findUserById(id).get().getName())) {
                        return ResponseEntity.badRequest().build();
                }

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                userService.deleteUser(user);

                return ResponseEntity.ok(user);

                // if the user ins`t found we will never reach this point so it is not necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete an User image")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User Image Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
        })
        @DeleteMapping("/{id}/image")
        public ResponseEntity<UserResponseDTO> deleteImage(@PathVariable long id) {

                User user = userService.findUserById(id).orElseThrow();

                user.setImageFile(null);
                user.setImage(false);

                userService.save(user);

                UserResponseDTO userDto = new UserResponseDTO(user);

                return ResponseEntity.ok(userDto);
        }

        @Operation(summary = "Get leagues of a user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found user leagues", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "user not found", content = @Content)
        })
        @GetMapping("/{id}/leagues")
        public ResponseEntity<List<LeagueDTO>> getUserLeagues(HttpServletRequest request, @PathVariable long id) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"))
                                .getName())) {
                        return ResponseEntity.badRequest().build();
                }
                // We can use directly the method .get() because we have already proved that the
                // user exists
                return ResponseEntity.ok(restService.findLeaguesByUser(user));
        }

        @Operation(summary = "Update an User leagues")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User leagues Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}/leagues/{leagueId}")
        public ResponseEntity<List<LeagueDTO>> addUserLeagues(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long leagueId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                League league = restService.findLeagueById(leagueId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe una liga con ese id"));

                userService.addUserLeague(user, league);

                return ResponseEntity.ok(restService.findLeaguesByUser(user));
        }

        @Operation(summary = "Delete an User league")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User league Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LeagueDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}/leagues/{leagueId}")
        public ResponseEntity<List<LeagueDTO>> deleteUserLeagues(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long leagueId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                League league = restService.findLeagueById(leagueId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

                userService.deleteUserLeague(user, league);

                return ResponseEntity.ok(restService.findLeaguesByUser(user));

                // if the league ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get teams of a user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found user teams", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "user not found", content = @Content)
        })
        @GetMapping("/{id}/teams")
        public ResponseEntity<List<TeamResponseDTO>> getUserTeams(HttpServletRequest request, @PathVariable long id) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                // We can use directly the method .get() because we have already proved that the
                // user exists
                return ResponseEntity.ok(restService.findTeamsByUser(user));
        }

        @Operation(summary = "Update an User matches")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User teams Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}/teams/{teamId}")
        public ResponseEntity<List<TeamResponseDTO>> addUserTeams(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long teamId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                Team team = restService.findTeamById(teamId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

                userService.addUserTeam(user, team);

                return ResponseEntity.ok(restService.findTeamsByUser(user));

                // if the team ins`t found we will never reach this point so it is not necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete an User team")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User team Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponseDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}/teams/{teamId}")
        public ResponseEntity<List<TeamResponseDTO>> deleteUserTeams(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long teamId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                Team team = restService.findTeamById(teamId)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un equipo con ese id"));

                userService.deleteUserTeam(user, team);

                return ResponseEntity.ok(restService.findTeamsByUser(user));

                // if the team ins`t found we will never reach this point so it is not necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Get players of a user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found user players", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "user not found", content = @Content)
        })
        @GetMapping("/{id}/players")
        public ResponseEntity<List<PlayerDTO>> getUserPlayers(HttpServletRequest request, @PathVariable long id) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                // We can use directly the method .get() because we have already proved that the
                // user exists
                return ResponseEntity.ok(restService.findPlayersByUser(user));
        }

        @Operation(summary = "Update an User players")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User playaers Updated", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
        })
        @PutMapping("/{id}/players/{playerId}")
        public ResponseEntity<List<PlayerDTO>> addUserPlayers(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long playerId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }
                Player player = restService.findPlayerById(playerId)
                                .orElseThrow(() -> new ElementNotFoundException("no existe un jugador con ese id"));

                userService.addUserPlayer(user, player);

                return ResponseEntity.ok(restService.findPlayersByUser(user));

                /// if the player ins`t found we will never reach this point so it is not
                /// necessary
                // to create a not found ResponseEntity
        }

        @Operation(summary = "Delete an User player")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User player Deleted", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))

                        }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
        })
        @DeleteMapping("/{id}/players/{playerId}")
        public ResponseEntity<List<PlayerDTO>> deleteUserPlayers(HttpServletRequest request, @PathVariable long id,
                        @PathVariable long playerId) {

                User user = userService.findUserById(id)
                                .orElseThrow(() -> new ElementNotFoundException("No existe un usuario con ese id"));

                if (!request.getUserPrincipal().getName().equals(user.getName())) {
                        return ResponseEntity.badRequest().build();
                }

                Player player = restService.findPlayerById(playerId)
                                .orElseThrow(() -> new ElementNotFoundException("no existe un jugador con ese id"));

                userService.deleteUserPlayer(user, player);

                return ResponseEntity.ok(restService.findPlayersByUser(user));

                // if the player ins`t found we will never reach this point so it is not
                // necessary
                // to create a not found ResponseEntity
        }
}
