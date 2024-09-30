package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.User;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByNameIgnoreCase(String name);

    Page<League> findAllByUser(User u, Pageable pageable);
}