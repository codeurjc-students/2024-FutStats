package com.tfg.futstats.controllers.dtos.player;

import com.tfg.futstats.models.Player;

public class PlayerResponseDTO {
    // dto attributes
    private long id;
    private String league;
    private String team;
    private String name;
    private int age;
    private String nationality;
    private String position;
    private boolean image;
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
    private int cards;
    private int yellowCards;
    private int redCards;

    // creation
    private int passes;
    private int goodPasses;
    private int shortPasses;
    private int longPasses;
    private int assists;
    private int dribles;
    private int centers;
    private int ballLosses;

    // Goalkeeper
    private int shootsReceived;
    private int goalsConceded;
    private int saves;
    private int outBoxSaves;
    private int inBoxSaves;

    public PlayerResponseDTO() {
    }

    public PlayerResponseDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.position = player.getPosition();
        this.league = player.getLeague().getName();
        this.team = player.getTeam().getName();
        this.totalMatches = player.getTotalMatches();
        this.totalShoots= player.getTotalShoots();
        this.totalGoals= player.getTotalGoals();
        this.penaltys= player.getPenaltys();
        this.faultsReceived= player.getFaultsReceived();
        this.offsides= player.getOffsides();
        this.commitedFaults= player.getCommitedFaults();
        this.recovers= player.getRecovers();
        this.duels= player.getDuels();
        this.wonDuels= player.getWonDuels();
        this.cards= player.getCards();
        this.yellowCards= player.getYellowCards();
        this.redCards= player.getRedCards();
        this.passes= player.getPasses();
        this.goodPasses= player.getGoodPasses();
        this.shortPasses= player.getShortPasses();
        this.longPasses= player.getLongPasses();
        this.assists= player.getAssists();
        this.dribles= player.getDribles();
        this.centers= player.getCenters();
        this.ballLosses= player.getBallLosses();
        this.shootsReceived= player.getShootsReceived();
        this.goalsConceded= player.getGoalsConceded();
        this.saves= player.getSaves();
        this.outBoxSaves= player.getOutBoxSaves();
        this.inBoxSaves= player.getInBoxSaves();
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean getImage() {
        return this.image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getTotalShoots() {
        return totalShoots;
    }

    public void setTotalShoots(int totalShoots) {
        this.totalShoots = totalShoots;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public int getPenaltys() {
        return penaltys;
    }

    public void setPenaltys(int penaltys) {
        this.penaltys = penaltys;
    }

    public int getFaultsReceived() {
        return faultsReceived;
    }

    public void setFaultsReceived(int faultsReceived) {
        this.faultsReceived = faultsReceived;
    }

    public int getOffsides() {
        return offsides;
    }

    public void setOffsides(int offsides) {
        this.offsides = offsides;
    }

    public int getCommitedFaults() {
        return commitedFaults;
    }

    public void setCommitedFaults(int commitedFaults) {
        this.commitedFaults = commitedFaults;
    }

    public int getRecovers() {
        return recovers;
    }

    public void setRecovers(int recovers) {
        this.recovers = recovers;
    }

    public int getDuels() {
        return duels;
    }

    public void setDuels(int duels) {
        this.duels = duels;
    }

    public int getWonDuels() {
        return wonDuels;
    }

    public void setWonDuels(int wonDuels) {
        this.wonDuels = wonDuels;
    }

    public int getCards() {
        cards = redCards + yellowCards;
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

    public int getGoodPasses() {
        return goodPasses;
    }

    public void setGoodPasses(int goodPasses) {
        this.goodPasses = goodPasses;
    }

    public int getShortPasses() {
        return shortPasses;
    }

    public void setShortPasses(int shortPasses) {
        this.shortPasses = shortPasses;
    }

    public int getLongPasses() {
        return longPasses;
    }

    public void setLongPasses(int longPasses) {
        this.longPasses = longPasses;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDribles() {
        return dribles;
    }

    public void setDribles(int dribles) {
        this.dribles = dribles;
    }

    public int getCenters() {
        return centers;
    }

    public void setCenters(int centers) {
        this.centers = centers;
    }

    public int getBallLosses() {
        return ballLosses;
    }

    public void setBallLosses(int ballLosses) {
        this.ballLosses = ballLosses;
    }

    public int getShootsReceived() {
        return shootsReceived;
    }

    public void getShootsReceived(int shootsReceived) {
        this.shootsReceived = shootsReceived;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getOutBoxSaves() {
        return outBoxSaves;
    }

    public void setOutBoxSaves(int outBoxSaves) {
        this.outBoxSaves = outBoxSaves;
    }

    public int getInBoxSaves() {
        return inBoxSaves;
    }

    public void setInBoxSaves(int inBoxSaves) {
        this.inBoxSaves = inBoxSaves;
    }

}
