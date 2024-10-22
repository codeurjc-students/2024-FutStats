package com.tfg.futstats.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.Match;


public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT p FROM Match p WHERE p.league.name = :league")
    Page<Match> findAllByLeague(@Param("league") String league , Pageable pageable);

    @Query("SELECT p FROM Match p WHERE p.team1.name = :name OR p.team2.name = :name")
    Page<Match> findAllByTeam(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Match p WHERE p.team1.name = :name OR p.team2.name = :name")
    int findMatchesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.shoots1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.shoots2 ELSE 0 END) FROM Match p")
    int findShootsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.scores1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.scores2 ELSE 0 END) FROM Match p")
    int findGoalsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.penaltys1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.penaltys2 ELSE 0 END) FROM Match p")
    int findPeanltysByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.faultsReceived1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.faultsReceived2 ELSE 0 END) FROM Match p")
    int findFaultsReceivedByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.offsides1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.offsides2 ELSE 0 END) FROM Match p")
    int findOffsidesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.commitedFaults1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.commitedFaults2 ELSE 0 END) FROM Match p")
    int findCommitedFaultsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.recovers1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.recovers2 ELSE 0 END) FROM Match p")
    int findRecoversByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.duels1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.duels2 ELSE 0 END) FROM Match p")
    int findDuelsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.wonDuels1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.wonDuels2 ELSE 0 END) FROM Match p")
    int findWonDuelsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.yellowCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.yellowCards2 ELSE 0 END) FROM Match p")
    int findYellowCardsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.redCards1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.redCards2 ELSE 0 END) FROM Match p")
    int findRedCardsByTeam(@Param("name") String name);

    //This query is a little bit different because we have to divide then by the number of matches played to have the promedium
    @Query("SELECT (SUM(CASE WHEN p.team1.name = :name THEN p.possesion1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.possesion2 ELSE 0 END)) /  + COUNT(p) FROM Match p WHERE p.team1.name = :name OR p.team2.name = :name")
    double findAveragePossessionByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.passes1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.passes2 ELSE 0 END) FROM Match p")
    int findPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.goodPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.goodPasses2 ELSE 0 END) FROM Match p")
    int findGoodPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.shortPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.shortPasses2 ELSE 0 END) FROM Match p")
    int findShortPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.longPasses1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.longPasses2 ELSE 0 END) FROM Match p")
    int findLongPassesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.assists1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.assists2 ELSE 0 END) FROM Match p")
    int findAssistsByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.dribles1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.dribles2 ELSE 0 END) FROM Match p")
    int findDriblesByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.centers1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.centers2 ELSE 0 END) FROM Match p")
    int findCentersByTeam(@Param("name") String name);

    @Query("SELECT SUM(CASE WHEN p.team1.name = :name THEN p.ballLosses1 ELSE 0 END) + SUM(CASE WHEN p.team2.name = :name THEN p.ballLosses2 ELSE 0 END) FROM Match p")
    int findBallLossesByTeam(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM Match p  WHERE (p.team1.name = :name AND p.scores1 > p.scores2) OR (p.team2.name = :name AND p.scores2 > p.scores1)")
    int findWonMatchesByTeam(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM Match p WHERE (p.team1.name = :name AND p.scores1 < p.scores2) OR (p.team2.name = :name AND p.scores2 < p.scores1)")
    int findLostMatchesByTeam(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM Match p WHERE (p.team1.name = :name AND p.scores1 = p.scores2) OR (p.team2.name = :name AND p.scores2 = p.scores1)")
    int findDrawMatchesByTeam(@Param("name") String name);

}
