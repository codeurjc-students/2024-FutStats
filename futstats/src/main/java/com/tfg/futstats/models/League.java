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
public class League 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    //League attributes
    private String name;
    private String president;
    private String nationality;

    //Constructors
    public League()
    {
        
    }

    public League(String name, String president, String nationality,User user)
    {
        this.name = name;
        this.president = president;
        this.nationality = nationality;
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

    public String getPresident()
    {
        return president;
    }

    public void setPresident(String president)
    {
        this.president = president;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }
    
}
