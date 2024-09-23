package com.tfg.futstats.controllers.DTOs;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;

public class PlayerDTO 
{  
   //dto attributes
   private long id;
   private League league;
   private Team team;
   private String name;
   private int age;
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

   public void setLeague(League league)
   {
      this.league = league;
   }

   public League getLeague()
   {
      return this.league;
   }

   public void setTeam(Team team)
   {
      this.team = team;
   }

   public Team getTeam()
   {
      return this.team;
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
