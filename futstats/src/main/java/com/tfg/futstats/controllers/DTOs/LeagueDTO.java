package com.tfg.futstats.controllers.DTOs;

public class LeagueDTO 
{
    //dto attributes
    private long id;
    private String name;
    private String president;
    private String nationality;

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
