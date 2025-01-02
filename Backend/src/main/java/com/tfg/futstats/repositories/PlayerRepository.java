package com.tfg.futstats.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.User;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Find a player by his name.
    Optional<Player> findByNameIgnoreCase(String name);

    List<Player> findAllByUsers(User user);

    List<Player> findPlayersByLeague(League league);

    List<Player> findPlayersByTeam(Team team);

    @Transactional
    @Modifying
    @Query("DELETE FROM Player m WHERE m.team.id = :teamId")
    void deleteByTeamId(Long teamId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Player m WHERE m.league.id = :leagueId")
    void deleteByLeagueId(Long leagueId);

}
