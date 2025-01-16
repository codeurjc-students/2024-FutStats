package com.tfg.futstats.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.tfg.futstats.controllers.dtos.user.UserDTO;
import com.tfg.futstats.controllers.dtos.user.UserResponseDTO;
import com.tfg.futstats.controllers.dtos.league.LeagueDTO;
import com.tfg.futstats.controllers.dtos.team.TeamResponseDTO;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;
import com.tfg.futstats.models.User;
import com.tfg.futstats.services.RestService;
import com.tfg.futstats.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class UserControllerUnitaryTest {
    
        @Mock
    private RestService restService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        List<UserResponseDTO> mockUsers = List.of(new UserResponseDTO());
        when(userService.findAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<UserResponseDTO>> response = userController.getUsers(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockUsers, response.getBody());
    }

    @Test
    void testMe() {
        User mockUser = new User();
        mockUser.setName("testuser");
        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserByName("testuser")).thenReturn(Optional.of(mockUser));

        ResponseEntity<UserResponseDTO> response = userController.me(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("testuser", response.getBody().getName());
    }

    @Test
    void testGetUserById() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setName("testuser");
        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        ResponseEntity<UserResponseDTO> response = userController.getUser(request, 1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testPostUser() {
        UserDTO userDto = new UserDTO();
        userDto.setName("newuser");
        UserResponseDTO mockUserResponse = new UserResponseDTO();
        mockUserResponse.setName("newuser");
        User user = new User();
        user.setId(1);

        when(userService.createUser(anyString(), anyString(), any(), anyBoolean(), anyList())).thenReturn(user);

        ResponseEntity<UserResponseDTO> response = userController.postUser(userDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("newuser", response.getBody().getName());
    }

    @Test
    void testPutUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setName("testuser");
        UserDTO userDto = new UserDTO();
        userDto.setName("updateduser");

        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        ResponseEntity<UserResponseDTO> response = userController.putUser(request, 1, userDto);

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).updateUser(mockUser, userDto);
    }

    @Test
    void testDeleteUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setName("testuser");

        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        ResponseEntity<User> response = userController.deleteUser(request, 1);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetLeaguesOfUser() {
        User mockUser = new User();
        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        List<LeagueDTO> mockLeagues = List.of(new LeagueDTO());
        when(restService.findLeaguesByUser(mockUser)).thenReturn(mockLeagues);

        ResponseEntity<List<LeagueDTO>> response = userController.getUserLeagues(request, 1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockLeagues, response.getBody());
    }

    @Test
    void testGetTeamsOfUser() {
        User mockUser = new User();
        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        List<TeamResponseDTO> mockTeams = List.of(new TeamResponseDTO());
        when(restService.findTeamsByUser(mockUser)).thenReturn(mockTeams);

        ResponseEntity<List<TeamResponseDTO>> response = userController.getUserTeams(request, 1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockTeams, response.getBody());
    }

    @Test
    void testGetPlayersOfUser() {
        User mockUser = new User();
        when(request.getUserPrincipal()).thenReturn(() -> "testuser");
        when(userService.findUserById(1)).thenReturn(Optional.of(mockUser));

        List<PlayerDTO> mockPlayers = List.of(new PlayerDTO());
        when(restService.findPlayersByUser(mockUser)).thenReturn(mockPlayers);

        ResponseEntity<List<PlayerDTO>> response = userController.getUserPlayers(request, 1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPlayers, response.getBody());
    }
}