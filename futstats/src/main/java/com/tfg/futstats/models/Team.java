package com.tfg.futstats.models;

import javax.management.InvalidAttributeValueException;

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

    public Team(String name, int trophies, String nationality,String trainer, String secondTrainer, String president, String stadium, int points)
    {
        this.name = name;
        this.trophies = trophies;
        this.nationality = nationality;
        this.trainer = trainer;
        this.secondTrainer = secondTrainer;
        this.president = president;
        this.stadium = stadium;
        this.points = points;
    }

    //Getters & Setters
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
