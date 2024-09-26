package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.League;


public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findAllByLeague(League league , Pageable pageable);
    Page<Match> findAllByTeam(Team team , Pageable pageable);
}
