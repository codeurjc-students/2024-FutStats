package com.tfg.futstats.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   // Find user by his name
   Optional<User> findByName(String name);

   Optional<User> findByNameIgnoreCase(String name);

   List<User> findAll();
}
