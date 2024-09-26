package com.tfg.futstats.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.leagueModels.League;
import com.example.demo.models.leagueModels.Player;
import com.tfg.futstats.models.User;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findAll(Pageable pageable);

    // Find a player by his name.
    Optional<Player> findByNameIgnoreCase(String name);

    Page<Player> findAllByUser(User u, Pageable pageable);
}
