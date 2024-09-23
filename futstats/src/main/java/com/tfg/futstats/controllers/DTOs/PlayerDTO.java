package com.tfg.futstats.controllers.DTOs;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;

public class PlayerDTO {
   // dto attributes
   private long id;
   private League league;
   private Team team;
   private String name;
   private int age;
   private String nationality;

   // player stats
   private int totalMatches;

   // offensive
   private int totalShoots;
   private int totalGoals;
   private int penaltys;
   private int faultsReceived;
   private int offsides;

   // defensive
   private int commitedFaults;
   private int recovers;
   private int duels;
   private int wonDuels;
   private int lostDuels;
   private int cards;
   private int yellowCards;
   private int redCards;

   // creation
   private int passes;
   private int goodPasses;
   private int badPasses;
   private int shortPasses;
   private int longPasses;
   private int assists;
   private int dribles;
   private int centers;
   private int ballLosses;

   // We dont put the averages here because we only need the stadistics from were
    // it`s made

   // Getters & setters
   public long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setLeague(League league) {
      this.league = league;
   }

   public League getLeague() {
      return this.league;
   }

   public void setTeam(Team team) {
      this.team = team;
   }

   public Team getTeam() {
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

   public String getAge() {
      return age;
   }

   public void setNationality(String nationality) {
      this.nationality = nationality;
   }

   public String getNationality() {
      return nationality;
   }

   public void setTotalMatches(int totalMatches) {
      this.totalMatches = totalMatches;
   }

   public int getTotalMatches() {
      return totalMatches;
   }

   public void setTotalShoots(int totalShoots) {
      this.totalShoots = totalShoots;
   }

   public int getTotalShoots() {
      return totalShoots;
   }

   public void setTotalGoals(int totalGoals) {
      this.totalGoals = totalGoals;
   }

   public int getTotalGoals() {
      return totalGoals;
   }

   public void setPenaltys(int penaltys) {
      this.penaltys = penaltys;
   }

   public int getPenaltys() {
      return penaltys;
   }

   public void setFaultsReceived(int faultsReceived) {
      this.faultsReceived = faultsReceived;
   }

   public int getFaultsReceived() {
      return faultsReceived;
   }

   public void setOffsides(int offsides) {
      this.offsides = offsides;
   }

   public int getOffsides() {
      return offsides;
   }

   public void setCommitedFaults(int commitedFaults) {
      this.commitedFaults = commitedFaults;
   }

   public int getCommitedFaults() {
      return commitedFaults;
   }

   public void setRecovers(int recovers) {
      this.recovers = recovers;
   }

   public int getRecovers() {
      return recovers;
   }

   public void setDuels(int duels) {
      this.duels = duels;
   }

   public int getDuels() {
      return duels;
   }

   public void setWonDuels(int wonDuels) {
      this.wonDuels = wonDuels;
   }

   public int getWonDuels() {
      return wonDuels;
   }

   public void setLostDuels(int lostDuels) {
      this.lostDuels = lostDuels;
   }

   public int getLostDuels() {
      return lostDuels;
   }

   public void setCards(int cards) {
      this.cards = cards;
   }

   public int getCards() {
      return cards;
   }

   public void setYellowCards(int yellowCards) {
      this.yellowCards = yellowCards;
   }

   public int getYellowCards() {
      return yellowCards;
   }

   public void setRedCards(int redCards) {
      this.redCards = redCards;
   }

   public int getRedCards() {
      return redCards;
   }

   public void setPasses(int passes) {
      this.passes = passes;
   }

   public int getPasses() {
      return passes;
   }

   public void setGoodPasses(int goodPasses) {
      this.goodPasses = goodPasses;
   }

   public int getGoodPasses() {
      return goodPasses;
   }

   public void setBadPasses(int badPasses) {
      this.badPasses = badPasses;
   }

   public int getBadPasses() {
      return badPasses;
   }

   public void setShortPasses(int shortPasses) {
      this.shortPasses = shortPasses;
   }

   public int getShortPasses() {
      return shortPasses;
   }

   public void setLongPasses(int longPasses) {
      this.longPasses = longPasses;
   }

   public int getLongPasses() {
      return longPasses;
   }

   public void setAssists(int assists) {
      this.assists = assists;
   }

   public int getAssists() {
      return assists;
   }

   public void setDribles(int dribles) {
      this.dribles = dribles;
   }

   public int getDribles() {
      return dribles;
   }

   public void setCenters(int centers) {
      this.centers = centers;
   }

   public int getCenters() {
      return centers;
   }

   public void setBallLosses(int ballLosses) {
      this.ballLosses = ballLosses;
   }

   public int getBallLosses() {
      return ballLosses;
   }
}
