package com.tfg.futstats.services;

import com.tfg.futstats.FutstatsApplication;
import com.tfg.futstats.controllers.dtos.user.UserDTO;
import com.tfg.futstats.controllers.dtos.user.UserResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.User;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FutstatsApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    RestService restService;

    @Test
    public void testFindAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();

        assertEquals(22, users.size());
        assertEquals("admin", users.get(0).getName());
        assertEquals("user0", users.get(1).getName());
    }

    @Test
    public void testFindUserById() {
        Optional<User> user = userService.findUserById(1);

        assertTrue(user.isPresent(), "User should be present");

        assertEquals("admin", user.get().getName());
    }

    @Test
    public void testFindUserByName() {
        Optional<User> user = userService.findUserByName("admin");

        assertTrue(user.isPresent(), "User should be present");

        assertEquals(1, user.get().getId());
    }

    @Test
    @Transactional
    public void testCreateUser() {
        try {
            byte[] imageBlob = convertImageToBlob("resources/static/piramide.jpeg");
            Blob blob = new SerialBlob(imageBlob);

            User user = new User("prueba", "pass", blob, true, "[user]","[admin]");

            userService.createUser(user);

            Optional<User> userU = userService.findUserById(22);

            assertTrue(userU.isPresent(), "User should be present");

            assertEquals("prueba", userU.get().getName());

        } catch (Exception e) {
            e.printStackTrace();
            // Acción alternativa
        }
    }

    @Test
    public void testUpdateUser() {
        UserDTO user = new UserDTO();
        user.setName("cambio");

        Optional<User> oldUser = userService.findUserById(21);

        assertTrue(oldUser.isPresent(), "User should be present");

        userService.updateUser(oldUser.get(), user);

        Optional<User> userU = userService.findUserById(21);

        assertTrue(userU.isPresent(), "User should be present");

        assertEquals("cambio", userU.get().getName());
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        Optional<User> oldUser = userService.findUserById(21);

        assertTrue(oldUser.isPresent(), "User should be present");

        userService.deleteUser(oldUser.get());

        Optional<User> userU = userService.findUserById(21);

        assertTrue(!userU.isPresent(), "User should not be present");
    }

    @Test
    public void testSaveuser(){

        User user = new User("prueba", "pass", null, false, "[user]");

        userService.save(user);

        Optional<User> userU = userService.findUserById(user.getId());

        assertTrue(userU.isPresent(), "User should be present");

        assertEquals("prueba", userU.get().getName());
    }

    @Test
    @Transactional
    public void testAddUserLeague(){
        userService.addUserLeague(userService.findUserById(1).get(), restService.findLeagueById(1).get());

        List<League> leagues = userService.findUserById(1).get().getLeagues();

        assertEquals(1, leagues.size());
        assertEquals("LaLiga", leagues.get(0).getName());
    }

    @Test
    @Transactional
    public void testDeleteUserLeague(){
        userService.addUserLeague(userService.findUserById(1).get(), restService.findLeagueById(1).get());

        userService.deleteUserLeague(userService.findUserById(1).get(), restService.findLeagueById(1).get());

        List<League> leagues = userService.findUserById(1).get().getLeagues();

        assertEquals(0, leagues.size());
    }

    @Test
    @Transactional
    public void testAddUserTeam(){
        userService.addUserTeam(userService.findUserById(1).get(), restService.findTeamById(1).get());

        List<Team> teams = userService.findUserById(1).get().getTeams();

        assertEquals(1, teams.size());
        assertEquals("Real Madrid", teams.get(0).getName());
    }

    @Test
    @Transactional
    public void testDeleteUserTeam(){
        userService.addUserTeam(userService.findUserById(1).get(), restService.findTeamById(1).get());

        userService.deleteUserTeam(userService.findUserById(1).get(), restService.findTeamById(1).get());

        List<Team> teams = userService.findUserById(1).get().getTeams();

        assertEquals(0, teams.size());
    }

    @Test
    @Transactional
    public void testAddUserPlayer(){
        userService.addUserPlayer(userService.findUserById(1).get(), restService.findPlayerById(1).get());

        List<Player> players = userService.findUserById(1).get().getPlayers();

        assertEquals(1, players.size());
        assertEquals("Vinicius Jr.", players.get(0).getName());
    }

    @Test
    @Transactional
    public void testDeleteUserPlayer(){
        userService.addUserPlayer(userService.findUserById(1).get(), restService.findPlayerById(1).get());
        
        userService.deleteUserPlayer(userService.findUserById(1).get(), restService.findPlayerById(1).get());

        List<Player> players = userService.findUserById(1).get().getPlayers();

        assertEquals(0, players.size());
    }

    public byte[] convertImageToBlob(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + imagePath, e);
        }
    }
}
