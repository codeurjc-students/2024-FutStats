package com.tfg.futstats.controllers.DTOs;

public class TeamDTO 
{
    //dto attributes
    private long id;
    private String name;
    private int trophies;
    private String nationality;
    private String trainer;
    private String secondTrainer;
    private String president;
    private String stadium;
    private int points;

    //Getters & setters
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
