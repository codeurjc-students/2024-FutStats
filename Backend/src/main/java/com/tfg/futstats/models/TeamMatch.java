package com.tfg.futstats.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.team.TeamMatchDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

@Entity
public class TeamMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    private String name;

    private String matchName;

    @ManyToOne
    @JsonIgnore
    private Team team;

    @ManyToOne
    @JsonIgnore
    private Match match;

    int points = 0;

    public TeamMatch () {}

    public TeamMatch(TeamMatchDTO teamMatchDto){
        this.name = teamMatchDto.getName();
        this.matchName = teamMatchDto.getMatchName();
        this.points = teamMatchDto.getPoints();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getPoints(){
        return this.points;
    }

    public void setPoints(int points){
        this.points = points;
    }

}
