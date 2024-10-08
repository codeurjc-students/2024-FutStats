package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.Match;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.League;


public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findAllByLeague(League league , Pageable pageable);
    Page<Match> findAllByTeam(Team team , Pageable pageable);

    @Query("SELECT COUNT(p) FROM Partido p WHERE p.team1.name = :name OR p.team2.name = :name")
    int findMatchesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.shoots1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.shoots2 ELSE 0 END) FROM Partido p")
    int findTotalShootsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.scores1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.scores2 ELSE 0 END) FROM Partido p")
    int findTotalGoalsByTeam(@Param("name") String name);

    //This query is a little bit different because we have to divide then by the number of matches played to have the promedium
    @Query("SELECT (SUM(CASE WHEN p.team1.name = :name THEN p.possesion1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.possesion2 ELSE 0 END)) /  + COUNT(p) FROM Partido p WHERE p.team1.name = :name OR p.team2.name = :name")
    double findAveragePossessionByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.passes1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.passes2 ELSE 0 END) FROM Partido p")
    int findTotalPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.goodPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.goodPasses2 ELSE 0 END) FROM Partido p")
    int findTotalGoodPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.faults1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.faults2 ELSE 0 END) FROM Partido p")
    int findTotalFaultsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.yellowCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.yellowCards2 ELSE 0 END) FROM Partido p")
    int findTotalYellowCardsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.redCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.redCards2 ELSE 0 END) FROM Partido p")
    int findTotalRedCardsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.offsides1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.offsides2 ELSE 0 END) FROM Partido p")
    int findTotalOffsidesByTeam(@Param("name") String name);
}
