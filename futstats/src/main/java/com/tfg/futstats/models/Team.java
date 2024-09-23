package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.tfg.futstats.controllers.DTOs.TeamDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Team 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    //Realtions with other models in DB
    @ManyToOne
    private League league;

    @OneToMany
    private List<Match> matches;

    @OneToMany
    private List<Player> players;

    //Team attributes
    private String name;
    private int trophies;
    private String nationality;
    private String trainer;
    private String secondTrainer;
    private String president;
    private String stadium;
    private int points;
 
    //Constructors
    public Team()
    {
        
    }

    public Team(League league, String name, int trophies, String nationality,String trainer, String secondTrainer, String president, String stadium, int points)
    {
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.league = league;
        this.name = name;
        this.trophies = trophies;
        this.nationality = nationality;
        this.trainer = trainer;
        this.secondTrainer = secondTrainer;
        this.president = president;
        this.stadium = stadium;
        this.points = points;
    }

    public Team(TeamDTO team)
    {
      this.name = team.getName();
      this.trophies = team.getTrophies();
      this.nationality = team.getNationality();
      this.trainer = team.getTrainer();
      this.secondTrainer = team.getSecondTrainer();
      this.president = team.getPresident();
      this.stadium = team.getStadium();
      this.points = team.getPoints();
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

    //--------------------------------------- MATCH ---------------------------------
    public List<Match> getMatches()
    {
        return this.matches;
    }

    public void setMatches(List<Match> matches)
    {
        this.matches = matches;
    }

    public void setMatch(Match match)
    {
        this.matches.add(match);
        match.setLeague(this);
    }

    public void deleteMatch(Match match)
    {
        this.matches.remove(match);
    }

    //--------------------------------------- PLAYER --------------------------------
    public List<Player> getPlayers()
    {
        return this.players;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    public void setPlayers(Player player)
    {
        this.players.add(player);
        player.setLeague(this);
    }

    public void deletePlayer(Player player)
    {
        this.players.remove(player);
    }

    //------------------------------------------------------------------------
    public long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTrophies(int trophies)
    {
        this.trophies = trophies;
    }
 
    public int getTrophies()
    {
        return trophies;    
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }
 
    public String getNationality()
    {
        return nationality;
    }

    public void setTrainer(String trainer)
    {
        this.trainer = trainer;
    }

    public String getTrainer()
    {
        return trainer;
    }

    public void setSecondTrainer(String secondTrainer)
    {
        this.secondTrainer = secondTrainer;
    }
 
    public String getSecondTrainer()
    {
        return secondTrainer;
    }

    public void setPresident(String president)
    {
        this.president = president;
    }

    public String getPresident()
    {
        return president;
    }

    public void setStadium(String stadium)
    {
        this.stadium = stadium;
    }

    public String getStadium()
    {
        return stadium;
    }
         
    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getPoints()
    {
        return points;
    }
}
