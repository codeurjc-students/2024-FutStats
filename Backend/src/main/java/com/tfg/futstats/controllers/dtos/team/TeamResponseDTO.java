package com.tfg.futstats.controllers.dtos.team;

import com.tfg.futstats.models.Team;

public class TeamResponseDTO {

    // region attributes
    private long id;
    private String name;
    public String league;
    public boolean image;
    private int trophies;
    private String nationality;
    private String trainer;
    private String secondTrainer;
    private String president;
    private String stadium;
    private int points;

    // team stats
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

    // matches
    private int wonMatches;
    private int lostMatches;
    private int drawMatches;
    // endregion

    // We dont put the averages here because we only need the stadistics from were
    // it`s made

    // region Constructors
    public TeamResponseDTO() {
    }

    public TeamResponseDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.league = team.getLeague().getName();
        this.image = team.getImage();
        this.trophies = team.getTrophies();
        this.nationality = team.getNationality();
        this.trainer = team.getTrainer();
        this.secondTrainer = team.getSecondTrainer();
        this.president = team.getPresident();
        this.stadium = team.getStadium();
        this.points = team.getPoints();
        this.totalMatches = team.getTotalMatches();
        this.totalShoots = team.getTotalShoots();
        this.totalGoals = team.getTotalGoals();
        this.penaltys = team.getPenaltys();
        this.faultsReceived = team.getFaultsReceived();
        this.offsides = team.getOffsides();
        this.commitedFaults = team.getCommitedFaults();
        this.recovers = team.getRecovers();
        this.duels = team.getDuels();
        this.wonDuels = team.getWonDuels();
        this.yellowCards = team.getYellowCards();
        this.redCards = team.getRedCards();
        this.passes = team.getPasses();
        this.goodPasses = team.getGoodPasses();
        this.shortPasses = team.getShortPasses();
        this.longPasses = team.getLongPasses();
        this.assists = team.getAssists();
        this.dribles = team.getDribles();
        this.centers = team.getCenters();
        this.ballLosses = team.getBallLosses();
        this.shootsReceived = team.getShootsReceived();
        this.goalsConceded = team.getGoalsConceded();
        this.saves = team.getSaves();
        this.outBoxSaves = team.getOutBoxSaves();
        this.inBoxSaves = team.getInBoxSaves();
        this.wonMatches = team.getWonMatches();
        this.lostMatches = team.getLostMatches();
        this.drawMatches = team.getDrawMatches();
    }
    // endregion

    // region Getters & setters
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getLeague() {
        return this.league;
    }

    public boolean getImage() {
        return this.image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getSecondTrainer() {
        return secondTrainer;
    }

    public void setSecondTrainer(String secondTrainer) {
        this.secondTrainer = secondTrainer;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public void setFaultsReceived(int fautlsReceived) {
        this.faultsReceived = fautlsReceived;
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

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
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

    public int getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(int wonMatches) {
        this.wonMatches = wonMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(int lostMatches) {
        this.lostMatches = lostMatches;
    }

    public int getDrawMatches() {
        return drawMatches;
    }

    public void setDrawMatches(int drawMatches) {
        this.drawMatches = drawMatches;
    }
    // endregion
}
