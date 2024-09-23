package com.tfg.futstats.controllers.DTOs;

import java.sql.Date;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;

public class MatchDTO 
{
    //dto attributes
    private long id;
    private League league;
    private Team team1;
    private Team team2;
    private Date date;
    private String place;

    //Getters & setters
    public long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setLeague(League league)
    {
        this.league = league;
    }

    public League getLeague()
    {
        return this.league;
    }

    public Team getTeam1()
    {
        return team1;
    }

    public void setTeam1(Team team1)
    {
        this.team1 = team1; 
    }

    public Team getTeam2()
    {
        return team2;
    }

    public void setTeam2(Team team2)
    {
        this.team2 = team2;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }
}
