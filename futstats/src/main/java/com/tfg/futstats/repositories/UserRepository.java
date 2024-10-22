package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.futstats.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // Find user by his name
   Optional<User> findByName(String name);

   Optional<User> findByNameIgnoreCase(String name);

   Page<User> findAll(Pageable pageable);
}
