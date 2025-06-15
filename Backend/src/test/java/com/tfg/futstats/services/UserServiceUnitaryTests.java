package com.tfg.futstats.services;

import com.tfg.futstats.models.User;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.repositories.UserRepository;

import jakarta.mail.MessagingException;

import com.tfg.futstats.controllers.dtos.user.UserDTO;
import com.tfg.futstats.controllers.dtos.user.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class UserServiceUnitaryTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User("user1", "password1", "example@gmail.com", null, false, "ROLE_USER");
        User user2 = new User("user2", "password2", "example@gmail.com", null, false, "ROLE_ADMIN");
        user1.setId(1);
        user2.setId(2);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserResponseDTO> users = userService.findAllUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getName());
        assertEquals("user2", users.get(1).getName());
    }

    @Test
    void testFindUserById() {
        User user = new User("user1", "password1", "example@gmail.com", null, false, "ROLE_USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getName());
    }

    @Test
    void testFindUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindUserByName() {
        User user = new User("user1", "password1", "example@gmail.com", null, false, "ROLE_USER");

        when(userRepository.findByName("user1")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserByName("user1");

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getName());
    }

    @Test
    void testCreateUser() {
        try{
        String rawPassword = "password1";
        String encodedPassword = "encodedPassword1";
        Blob image = null;
        User user = new User("user1", encodedPassword, "example@gmail.com", image, false, "[user]");

        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("user1", result.getName());
        } catch (MessagingException e) {
        fail("MessagingException should not be thrown during the test");
    }

    }

    @Test
    void testUpdateUser() {
        User existingUser = new User("user1", "password1", "example@gmail.com", null, false, "ROLE_USER");
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setName("newUser1");
        String encodedPassword = "encodedNewPassword1";
        updatedUserDTO.setPassword(encodedPassword);

        when(passwordEncoder.encode(updatedUserDTO.getPassword())).thenReturn(encodedPassword);

        userService.updateUser(existingUser, updatedUserDTO);

        assertEquals("newUser1", existingUser.getName());
        assertEquals(encodedPassword, existingUser.getPassword());
        verify(userRepository).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        User user = mock(User.class);

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void testAddUserLeague() {
        User user = mock(User.class);
        League league = mock(League.class);

        userService.addUserLeague(user, league);

        verify(user).setLeague(league);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserLeague() {
        User user = mock(User.class);
        League league = mock(League.class);

        userService.deleteUserLeague(user, league);

        verify(user).removeLeague(league);
        verify(userRepository).save(user);
    }

    @Test
    void testAddUserTeam() {
        User user = mock(User.class);
        Team team = mock(Team.class);

        userService.addUserTeam(user, team);

        verify(user).setTeam(team);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserTeam() {
        User user = mock(User.class);
        Team team = mock(Team.class);

        userService.deleteUserTeam(user, team);

        verify(user).removeTeam(team);
        verify(userRepository).save(user);
    }

    @Test
    void testAddUserPlayer() {
        User user = mock(User.class);
        Player player = mock(Player.class);

        userService.addUserPlayer(user, player);

        verify(user).setPlayer(player);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserPlayer() {
        User user = mock(User.class);
        Player player = mock(Player.class);

        userService.deleteUserPlayer(user, player);

        verify(user).removePlayer(player);
        verify(userRepository).save(user);
    }
}
