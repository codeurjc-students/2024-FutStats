package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.leagueModels.League;
import com.example.demo.models.leagueModels.Player;
import com.tfg.futstats.models.User;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Page<League> findAll(Pageable pageable);

    Optional<League> findByNameIgnoreCase(String name);

    Page<Player> findAllByUser(User u, Pageable pageable);
}