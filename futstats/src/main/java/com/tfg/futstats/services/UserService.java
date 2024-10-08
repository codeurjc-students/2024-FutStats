package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.UserRepository;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.Player;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    public User createUser(String userName, String password) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void updateUser(long id, User updatedUser) {
        User oldUser = findUserById(id).get();
        updatedUser.setLeagues(oldUser.getLeagues());
        updatedUser.setTeams(oldUser.getTeams());
        updatedUser.setPlayers(oldUser.getPlayers());
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void addUserLeague(long id, League league){
       Optional<User> user = userRepository.findById(id);
       user.get().setLeague(league);
    }

    public void deleteUserLeague(long id, League league){
        Optional<User> user = userRepository.findById(id);
        user.get().removeLeague(league);
    }

    public void addUserTeam(long id, Team team){
        Optional<User> user = userRepository.findById(id);
        user.get().setTeam(team);
    }
 
     public void deleteUserTeam(long id, Team team){
         Optional<User> user = userRepository.findById(id);
         user.get().removeTeam(team);
    }

    public void addUserPlayer(long id, Player player){
        Optional<User> user = userRepository.findById(id);
        user.get().setPlayer(player);
    }
 
     public void deleteUserPlayer(long id, Player player){
         Optional<User> user = userRepository.findById(id);
         user.get().removePlayer(player);
    }
}
