package com.tfg.futstats.controllers.dtos;

public class PlayerDTO {
   // dto attributes
   private long id;
   private String league;
   private String team;
   private String name;
   private int age;
   private String nationality;

   // Getters & setters
   public long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setLeague(String league) {
      this.league = league;
   }

   public String getLeague() {
      return this.league;
   }

   public void setTeam(String team) {
      this.team = team;
   }

   public String getTeam() {
      return this.team;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public int getAge() {
      return age;
   }

   public void setNationality(String nationality) {
      this.nationality = nationality;
   }

   public String getNationality() {
      return nationality;
   }
}
