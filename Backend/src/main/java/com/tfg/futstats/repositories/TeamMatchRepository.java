package com.tfg.futstats.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.TeamMatch;


public interface TeamMatchRepository extends JpaRepository<TeamMatch, Long>  {
    
    List<TeamMatch> findByTeamId(Long teamId);
    
    List<TeamMatch> findByMatchId(Long matchId);

    @Query("SELECT t FROM TeamMatch t WHERE t.match.id = :match AND t.team.id = :team")
    TeamMatch findByMatchAndTeamId(@Param("match") Long match, @Param("team") Long team);
}
