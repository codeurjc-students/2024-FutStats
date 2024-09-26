package com.tfg.futstats.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.User;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Find a player by his name.
    Optional<Player> findByNameIgnoreCase(String name);

    Page<Player> findAllByUser(User u, Pageable pageable);

    Page<Player> findPlayersByLeague(League league, Pageable pageable);

    Page<Player> findPlayersByTeam(Team team, Pageable pageable);
}
