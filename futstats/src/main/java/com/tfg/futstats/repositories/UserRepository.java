package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.futstats.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // Find user by his name
   Optional<User> findByNameIgnoreCase(String name);
}
