package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    // Find a team by his name.
    Optional<Team> findByNameIgnoreCase(String name);

    List<Team> findAllByUsers(User user);

    List<Team> findTeamsByLeague(League league);
}
