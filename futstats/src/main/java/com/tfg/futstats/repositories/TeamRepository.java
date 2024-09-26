package com.tfg.futstats.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;

public interface TeamRepository extends JpaRepository<Team, Long> {
    // Find a team by his name.
    Optional<Team> findByNameIgnoreCase(String name);

    Page<Team> findAllByUser(User u, Pageable pageable);

    Page<Team> findTeamsByLeague(League league, Pageable pageable);
}
