package com.tfg.futstats.models;

import java.sql.Date;

import javax.management.InvalidAttributeValueException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Match 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    //Realtions with other models in DB
    @ManyToOne
    private League league;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    //Match attributes
    private Date date;
    private String place;

    //Constructors
    public Match()
    {
        
    }

    public Match(League league, Team team1, Team team2, Date date, String place)
    {
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.place = place;
    }

    //Getters & Setters
    //------------------------------------ LEAGUE ------------------------------------- 
    public void setLeague(League league)
    {
        this.league = league;
    }

    public League getLeague()
    {
        return this.league;
    }

    public void updateLeague(League league)
    {
        this.league = league;
    }

    public void deleteLeague()
    {
        this.league = null;
    }

    //------------------------------------ TEAM1 ------------------------------------- 
    public void setTeam1(Team team1)
    {
        this.team1 = team1;
    }

    public Team getTeam1()
    {
        return this.team1;
    }

    //team1 doesn`t need delete or update because there can´t be only one team in a match and there can´t be team change in a single match
    //------------------------------------ TEAM2 ------------------------------------- 
    public void setTeam2(Team team2)
    {
        this.team2 = team2;
    }

    public Team getTeam2()
    {
        return this.team2;
    }

    //team2 doesn`t need delete or update because there can´t be only one team in a match and there can´t be team change in a single match
    //------------------------------------------------------------------------
    public long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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
