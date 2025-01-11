package com.tfg.futstats.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByNameIgnoreCase(String name);

    List<League> findAllByUsers(User user);

    

}