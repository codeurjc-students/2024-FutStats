package com.tfg.futstats.controllers.dtos;

public class PlayerMatchDTO {

    private long id;

   // offensive
   private int shoots;
   private int goals;
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

   public int getShoots() {
    return shoots;
    }

   public void setShoots(int shoots) {
      this.shoots = shoots;
   }

   public void setGoals(int goals) {
      this.goals = goals;
   }

   public int getGoals() {
      return goals;
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
