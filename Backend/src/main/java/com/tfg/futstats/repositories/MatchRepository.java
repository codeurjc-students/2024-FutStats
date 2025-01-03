package com.tfg.futstats.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.Match;


public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findMatchByName(String name);

    @Query("SELECT p FROM Match p WHERE p.league.id = :league")
    List<Match> findAllByLeague(@Param("league") Long league);

    @Query("SELECT p FROM Match p WHERE p.team1.id = :id OR p.team2.id = :id")
    List<Match> findAllByTeam(@Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Match p WHERE p.team1.id = :id OR p.team2.id = :id")
    int findMatchesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.shoots1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.shoots2 ELSE 0 END) FROM Match p")
    int findShootsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.scores1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.scores2 ELSE 0 END) FROM Match p")
    int findGoalsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.penaltys1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.penaltys2 ELSE 0 END) FROM Match p")
    int findPeanltysByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.faultsReceived1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.faultsReceived2 ELSE 0 END) FROM Match p")
    int findFaultsReceivedByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.offsides1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.offsides2 ELSE 0 END) FROM Match p")
    int findOffsidesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.commitedFaults1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.commitedFaults2 ELSE 0 END) FROM Match p")
    int findCommitedFaultsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.recovers1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.recovers2 ELSE 0 END) FROM Match p")
    int findRecoversByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.duels1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.duels2 ELSE 0 END) FROM Match p")
    int findDuelsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.wonDuels1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.wonDuels2 ELSE 0 END) FROM Match p")
    int findWonDuelsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.yellowCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.yellowCards2 ELSE 0 END) FROM Match p")
    int findYellowCardsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.redCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.redCards2 ELSE 0 END) FROM Match p")
    int findRedCardsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.passes1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.passes2 ELSE 0 END) FROM Match p")
    int findPassesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.goodPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.goodPasses2 ELSE 0 END) FROM Match p")
    int findGoodPassesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.shortPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.shortPasses2 ELSE 0 END) FROM Match p")
    int findShortPassesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.longPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.longPasses2 ELSE 0 END) FROM Match p")
    int findLongPassesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.assists1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.assists2 ELSE 0 END) FROM Match p")
    int findAssistsByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.dribles1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.dribles2 ELSE 0 END) FROM Match p")
    int findDriblesByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.centers1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.centers2 ELSE 0 END) FROM Match p")
    int findCentersByTeam(@Param("id") Long id);

    @Query("SELECT SUM(CASE WHEN p.team1.id = :id THEN p.ballLosses1 ELSE 0 END) + SUM(CASE WHEN p.team2.id = :id THEN p.ballLosses2 ELSE 0 END) FROM Match p")
    int findBallLossesByTeam(@Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Match p  WHERE (p.team1.id = :id AND p.scores1 > p.scores2) OR (p.team2.id = :id AND p.scores2 > p.scores1)")
    int findWonMatchesByTeam(@Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Match p WHERE (p.team1.id = :id AND p.scores1 < p.scores2) OR (p.team2.id = :id AND p.scores2 < p.scores1)")
    int findLostMatchesByTeam(@Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Match p WHERE (p.team1.id = :id AND p.scores1 = p.scores2) OR (p.team2.id = :id AND p.scores2 = p.scores1)")
    int findDrawMatchesByTeam(@Param("id") Long id);
}
