package com.tfg.futstats.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.TeamMatch;

@Repository
public interface TeamMatchRepository extends JpaRepository<TeamMatch, Long>  {
    
    List<TeamMatch> findByTeamId(Long teamId);
    
    List<TeamMatch> findByMatchId(Long matchId);

    @Query("SELECT t FROM TeamMatch t WHERE t.match.id = :match AND t.team.id = :team")
    TeamMatch findByMatchAndTeamId(@Param("match") long match, @Param("team") long team);
}
