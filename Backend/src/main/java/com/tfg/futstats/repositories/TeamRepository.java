package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    // Find a team by his name.
    Optional<Team> findByNameIgnoreCase(String name);

    List<Team> findAllByUsers(User user);

    @Query("SELECT p FROM Team p WHERE p.league.id = :league ORDER BY p.points DESC, p.name ")
    List<Team> findTeamsByLeague(@Param("league") Long league);

    @Transactional
    @Modifying
    @Query("DELETE FROM Team m WHERE m.league.id = :leagueId")
    void deleteByLeagueId(Long leagueId);

    List<Team> findByLeagueId(Long leagueId);
}
