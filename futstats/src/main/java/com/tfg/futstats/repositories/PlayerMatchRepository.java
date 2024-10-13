package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.PlayerMatch;

public interface PlayerMatchRepository extends JpaRepository<PlayerMatch, Long>  {
    
    // Player stats 
    @Query("SELECT SUM(jp.match) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int matchesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.shoots) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int shootsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.goals) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int goalsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.peanltys) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int penaltysByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.faultsReceived) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int faultsReceivedByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.offsides) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int offsidesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.commitedFaults) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int commitedFaultsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.recovers) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int recoversByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.duels) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int duelsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.wonDuels) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int wonDuelsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.yellowCards) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int yellowCardsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.redCards) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int redCardsByPlayer(@Param("playerId") Long playerId);
    
    @Query("SELECT SUM(jp.passes) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int passesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.gooodPasses) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int goodPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.shortPasses) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int shortPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.longPasses) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int longPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.assists) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int assistsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.dribles) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int driblesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.centers) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int centersByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT SUM(jp.ballLosses) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int ballLossesByPlayer(@Param("playerId") Long playerId);

    // Match stats
    @Query("SELECT SUM(jp.shoots) FROM PlayerMatch jp WHERE jp.partido.id = :partidoId AND jp.player.team.id = :teamId")
    int shootsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.goals) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int goalsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.penaltys) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int penaltysByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.faultsReceived) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int faultsReceivedByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.offsides) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int offsidesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.commitedFaults) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int commitedFaultsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.recovers) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int recoversByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.duels) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int duelsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.wonDuels) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int wonDuelsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.yellowCards) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int yellowCardsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.redCards) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int redCardsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.passes) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int passesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.goodPasses) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int goodPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.shortPasses) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int shortPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.longPasses) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int longPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.assists) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int assistsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.dribles) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int driblesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.centers) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int centersByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT SUM(jp.ballLosses) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int ballLossesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);
}
