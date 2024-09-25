package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.leagueModels.League;
import com.example.demo.models.leagueModels.Player;
import com.example.demo.models.leagueModels.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAll(Pageable pageable);

    // Find a team by his name.
    Optional<Team> findByNameIgnoreCase(String name);
}
