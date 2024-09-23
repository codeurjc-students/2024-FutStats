package com.tfg.futstats.controllers.DTOs;

public class PlayerDTO 
{
    private String name;
    private int age;
    private String nationality;

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
