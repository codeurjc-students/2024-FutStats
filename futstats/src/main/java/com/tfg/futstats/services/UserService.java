package com.tfg.futstats.services;

import java.util.Optional;

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

    public User createUser(String userName, String password) {
        User user = new User(userName, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public void replaceUser(String userName, User updatedUser) {
        User oldUser = findUserByName(userName).get();
        updatedUser.setId(oldUser.getId());
        userRepository.save(updatedUser);
    }
}
