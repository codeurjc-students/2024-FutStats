package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.leagueModels.League;
import com.example.demo.models.leagueModels.Match;
import com.example.demo.models.leagueModels.Player;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findAll(Pageable pageable);
}
