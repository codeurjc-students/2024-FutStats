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

    //Match attributes
    private Date date;
    private String place;

    //Constructors
    public Match()
    {
        
    }

    public Match(Date date, String place)
    {
        this.date = date;
        this.place = place;
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
