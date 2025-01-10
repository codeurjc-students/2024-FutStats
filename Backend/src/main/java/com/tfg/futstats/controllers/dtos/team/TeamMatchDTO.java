package com.tfg.futstats.controllers.dtos.team;

import com.tfg.futstats.models.TeamMatch;

public class TeamMatchDTO {

    private long id;
    private String name;
    private String matchName;
    private long match;
    private int points;

    public TeamMatchDTO() {
    }

    public TeamMatchDTO(TeamMatch team) {
        this.id = team.getId();
        this.name = team.getName();
        this.matchName = team.getMatchName();
        this.match = team.getMatch().getId();
        this.points = team.getPoints();
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

    public long getMatch() {
        return this.match;
    }

    public void setMatch(long match) {
        this.match = match;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
