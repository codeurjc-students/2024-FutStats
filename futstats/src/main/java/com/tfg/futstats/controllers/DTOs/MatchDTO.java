package com.tfg.futstats.controllers.DTOs;

import java.sql.Date;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;

public class MatchDTO {
    // dto attributes
    private long id;
    private League league;
    private Team team1;
    private Team team2;
    private Date date;
    private String place;

    // Team1 attributes
    private int shoots1;
    private int scores1;
    private double possesion1;
    private int passes1;
    private int goodPasses1;
    private int faults1;
    private int yellowCards1;
    private int redCards1;
    private int offsides1;

    // Team2 attributes
    private int shoots2;
    private int scores2;
    private double possesion2;
    private int passes2;
    private int goodPasses2;
    private int faults2;
    private int yellowCards2;
    private int redCards2;
    private int offsides2;

    // We dont put the averages here because we only need the stadistics from were
    // it`s made

    // Getters & setters
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public League getLeague() {
        return this.league;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getShoots1()
    {
        return shoots1;
    }

    public void setShoots1(int shoots1)
    {
        this.shoots1 = shoots1;
    }

    public int getScoress1()
    {
        return scores1;
    }

    public void setScores1(int scores1)
    {
        this.scores1 = scores1;
    }

    public double getPossesion1()
    {
        return possesion1;
    }

    public void setPossesion1(double possesion1)
    {
        this.possesion1 = possesion1
    }

    public int getPasses1()
    {
        return passes1;
    }

    public void setPasses1(int passes1)
    {
        this.passes1 = passes1;
    }

    public int getGoodPasses1()
    {
        return goodPasses1;
    }

    public void setGoodPasses1(int goodPasses1)
    {
        this.goodPasses1 = goodPasses1;
    }

    public int getFaults1()
    {
        return faults1;
    }

    public void setFaults1(int faults1)
    {
        this.faults1 = faults1;
    }
    
    public int getYellowCards1()
    {
        return yellowCards1;
    }

    public void setYellowCards1(int yellowCards1)
    {
        return yellowCards1;
    }

    public int getRedCards1()
    {
        return redCards1;
    }

    public void setRedCards1(int redCards1)
    {
        this.redCards1 = redCards1;
    }

    public int getOffsides1()
    {
        return offsides1;
    }

    public void setOffsides1(int offsides1)
    {
        this.offsides1 = offsides1;
    }

    public int getShoots2()
    {
        return shoots2;
    }

    public void setShoots2(int shoots2)
    {
        this.shoots2 = shoots2;
    }

    public int getScoress2()
    {
        return scores2;
    }

    public void setScores2(int scores2)
    {
        this.scores2 = scores2;
    }

    public double getPossesion2()
    {
        return possesion2;
    }

    public void setPossesion2(double possesion2)
    {
        this.possesion2 = possesion2;
    }

    public int getPasses2()
    {
        return passes2;
    }

    public void setPasses2(int passes2)
    {
        this.passes2 = passes2;
    }

    public int getGoodPasses2()
    {
        return goodPasses2;
    }

    public void setGoodPasses2(int goodPasses2)
    {
        this.goodPasses2 = goodPasses2;
    }

    public int getFaults2()
    {
        return faults2;
    }

    public void setFaults2(int faults2)
    {
        this.faults2 = faults2;
    }
    
    public int getYellowCards2()
    {
        return yellowCards2;
    }

    public void setYellowCards2(int yellowCards2)
    {
        return yellowCards2;
    }

    public int getRedCards2()
    {
        return redCards2;
    }

    public void setRedCards2(int redCards2)
    {
        this.redCards2 = redCards2;
    }

    public int getOffsides2()
    {
        return offsides2;
    }

    public void setOffsides2(int offsides2)
    {
        this.offsides2 = offsides2;
    }
}
