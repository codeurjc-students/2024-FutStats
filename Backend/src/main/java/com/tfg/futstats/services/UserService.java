package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;

import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.UserRepository;
import com.tfg.futstats.controllers.dtos.user.UserResponseDTO;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.controllers.dtos.user.UserDTO;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestService restService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponseDTO(user))
                .toList();
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    public User createUser(String userName, String password, Blob imageFile, boolean image, List<String> roles) {
        User user = new User(userName, passwordEncoder.encode(password), imageFile, image, roles.toString());
        return userRepository.save(user);
    }

    public void updateUser(User oldUser, UserDTO updatedUser) {
        if(updatedUser.getName() != null){
            oldUser.setName(updatedUser.getName());
        }
        if(updatedUser.getPassword() != null){
            oldUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(oldUser);
    }

    public void deleteUser(User user) {

        if (user.getLeagues() != null) {
            List<League> leaguesToRemove = new ArrayList<>(user.getLeagues());
            for (League league : leaguesToRemove) {
                league.deleteUser(user);
                deleteUserLeague(user, league);
            }
        }

        if (user.getTeams() != null) {
            List<Team> teamsToRemove = new ArrayList<>(user.getTeams());
            for (Team team : teamsToRemove) {
                team.deleteUser(user);
                deleteUserTeam(user, team);
            }
        }

        if (user.getPlayers() != null) {
            List<Player> playersToRemove = new ArrayList<>(user.getPlayers());
            for (Player player : playersToRemove) {
                player.deleteUser(user);
                deleteUserPlayer(user,player);
            }
        }

        userRepository.delete(user);
    }

    public void save(User user){
        userRepository.save(user);
    }  

    public void addUserLeague(User user, League league){
       user.setLeague(league);
       userRepository.save(user);
    }

    public void deleteUserLeague(User user, League league){
        user.removeLeague(league);
        userRepository.save(user);
    }

    public void addUserTeam(User user, Team team){
        user.setTeam(team);
        userRepository.save(user);
    }
 
     public void deleteUserTeam(User user, Team team){
         user.removeTeam(team);
         userRepository.save(user);
    }

    public void addUserPlayer(User user, Player player){
        user.setPlayer(player);
        userRepository.save(user);
    }
 
     public void deleteUserPlayer(User user, Player player){
         user.removePlayer(player);
         userRepository.save(user);
    }
}
