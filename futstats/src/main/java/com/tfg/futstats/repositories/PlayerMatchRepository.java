package com.tfg.futstats.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tfg.futstats.models.PlayerMatch;

public interface PlayerMatchRepository extends JpaRepository<PlayerMatch, Long>  {
    
    // Player stats 
    @Query("SELECT COALESCE(COUNT(jp),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int matchesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.shoots),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int shootsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.goals),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int goalsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.penaltys),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int penaltysByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.faultsReceived),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int faultsReceivedByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.offsides),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int offsidesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.commitedFaults),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int commitedFaultsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.recovers),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int recoversByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.duels),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int duelsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.wonDuels),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int wonDuelsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.yellowCards),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int yellowCardsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.redCards),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int redCardsByPlayer(@Param("playerId") Long playerId);
    
    @Query("SELECT COALESCE(SUM(jp.passes),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int passesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.goodPasses),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int goodPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.shortPasses),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int shortPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.longPasses),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int longPassesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.assists),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int assistsByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.dribles),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int driblesByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.centers),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int centersByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT COALESCE(SUM(jp.ballLosses),0) FROM PlayerMatch jp WHERE jp.player.id = :playerId")
    int ballLossesByPlayer(@Param("playerId") Long playerId);

    // Match stats
    @Query("SELECT COALESCE(SUM(jp.shoots),0) FROM PlayerMatch jp WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int shootsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.goals),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int goalsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.penaltys),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int penaltysByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.faultsReceived),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int faultsReceivedByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.offsides),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int offsidesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.commitedFaults),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int commitedFaultsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.recovers),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int recoversByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.duels),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int duelsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.wonDuels),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int wonDuelsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.yellowCards),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int yellowCardsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.redCards),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int redCardsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.passes),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int passesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.goodPasses),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int goodPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.shortPasses),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int shortPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.longPasses),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int longPassesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.assists),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int assistsByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.dribles),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int driblesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.centers),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int centersByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    @Query("SELECT COALESCE(SUM(jp.ballLosses),0) FROM PlayerMatch jp  WHERE jp.match.id = :matchId AND jp.player.team.id = :teamId")
    int ballLossesByTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId);
}
