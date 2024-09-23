package com.tfg.futstats.models;

import javax.management.InvalidAttributeValueException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Player 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    //  player info
    private String name;
    private int age;
    private String nationality;

    //Constructors
    public Player()
    {
         
    }
 
    public Player(String name, int age, String nationality)
    {
        this.name = name;
        this.age = age;
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

    public void setAge(int age)
     {
        this.age = age;
     }

     public String getAge()
     {
        return age;    
     }

     public void setNationality(String nationality)
     {
        this.nationality = nationality;
     }

     public String getNationality()
     {
        return nationality;
     }
}
