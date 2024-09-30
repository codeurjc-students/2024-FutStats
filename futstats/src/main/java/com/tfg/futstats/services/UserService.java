package com.tfg.futstats.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.UserRepository;

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

    public void replaceUser(String userName, User updatedUser) {
        User oldUser = findUserByName(userName).get();
        updatedUser.setId(oldUser.getId());
        userRepository.save(updatedUser);
    }
}
