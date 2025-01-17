package com.tfg.futstats.controllers.dtos.player;



import com.tfg.futstats.models.Player;

public class PlayerDTO {
   // dto attributes
   private long id;
   private String league;
   private String team;
   private String name;
   private int age;
   private String nationality;
   private String position;
   private boolean image;

   public PlayerDTO(){}

   public PlayerDTO(Player player){
      this.id = player.getId();
      this.name = player.getName();
      this.age = player.getAge();
      this.nationality = player.getNationality();
      this.position = player.getPosition();
      this.league = player.getLeague().getName();
      this.team = player.getTeam().getName();
   }


   // Getters & setters
   public long getId() {
      return id;
   }

   public void setId(long id) {
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

   public String getPosition(){
      return position;
  }

  public void setPosition(String position){
      this.position = position;
  }

  public boolean getImage() {
   return this.image;
}

public void setImage(boolean image) {
   this.image = image;
}
}
