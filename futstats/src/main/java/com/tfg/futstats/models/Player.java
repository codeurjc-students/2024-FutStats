package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.tfg.futstats.controllers.DTOs.PlayerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    // Realtions with other models in DB
    @ManyToOne
    private League league;

    @ManyToOne
    private Team team;

    @ManyToMany
    private List<User> users;

    // player info
    private String name;
    private int age;
    private String nationality;

    // player stats
    private int totalMatches;

    // offensive
    private int totalShoots;
    private int totalGoals;
    private double shootsPerMatch;
    private double goalsPerMatch;
    private double scoreAvg;
    private int penaltys;
    private int faultsReceived;
    private int offsides;

    // defensive
    private int commitedFaults;
    private int recovers;
    private int duels;
    private int wonDuels;
    private int lostDuels;
    private double duelAvg;
    private int cards;
    private int yellowCards;
    private int redCards;

    // creation
    private int passes;
    private double passesPerMatch;
    private int goodPasses;
    private int badPasses;
    private double passesAvg;
    private int shortPasses;
    private int longPasses;
    private int assists;
    private int dribles;
    private int centers;
    private int ballLosses;

    // Constructors
    public Player() {

    }

    public Player(League league,
            Team team,
            String name,
            int age,
            String nationality,
            int totalMatches,
            int totalShoots,
            int totalGoals,
            int penaltys,
            int fautlsReceived,
            int offsides,
            int commitedFaults,
            int recovers,
            int duels,
            int wonDuels,
            int lostDuels,
            int cards,
            int yellowCrads,
            int redCards,
            int passes,
            int goodPasses,
            int badPasses,
            int shortPasses,
            int longPasses,
            int assists,
            int dribles,
            int centers,
            int ballLosses) {
        this.league = league;
        this.team = team;
        this.users = new ArrayList<User>();
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.totalMatches = totalMatches;
        this.totalShoots = totalShoots;
        this.totalGoals = totalGoals;
        this.penaltys = penaltys;
        this.fautlsReceived = fautlsReceived;
        this.offsides = offsides;
        this.commitedFaults = commitedFaults;
        this.recovers = recovers;
        this.duels = duels;
        this.wonDuels = wonDuels;
        this.lostDuels = lostDuels;
        this.cards = cards;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.passes = passes;
        this.goodPasses = goodPasses;
        this.badPasses = badPasses;
        this.shortPasses = shortPasses;
        this.longPasses = longPasses;
        this.assists = assists;
        this.dribles = dribles;
        this.centers = centers;
        this.ballLosses = ballLosses;
    }

    public Player(PlayerDTO player) {
        this.league = player.getLeague();
        this.team = player.getTeam();
        this.name = player.getName();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.totalMatches = player.getTotalMatches();
        this.totalShoots = player.getTotalShoots();
        this.totalGoals = player.getTotalGoals();
        this.penaltys = player.getPenaltys();
        this.fautlsReceived = player.getFaultsReceived();
        this.offsides = player.getOffsides();
        this.commitedFaults = player.getCommitedFaults();
        this.recovers = player.getRecovers();
        this.duels = player.getDuels();
        this.wonDuels = player.getWonDuels();
        this.lostDuels = player.getLostDuels();
        this.cards = player.getCards();
        this.yellowCards = player.getYellowCards();
        this.redCards = player.getRedCards();
        this.passes = player.getPasses();
        this.goodPasses = player.getGoodPasses();
        this.badPasses = player.getBadPasses();
        this.shortPasses = player.getShortPasses();
        this.longPasses = player.getLongPasses();
        this.assists = player.getAssists();
        this.dribles = player.getDribles();
        this.centers = player.getCenters();
        this.ballLosses = player.getBallLosses();
    }

    // Getters & Setters
    // ------------------------------------ LEAGUE
    // -------------------------------------
    public void setLeague(League league) {
        this.league = league;
    }

    public League getLeague() {
        return this.league;
    }

    public void updateLeague(League league) {
        this.league = league;
    }

    public void deleteLeague() {
        this.league = null;
    }

    // ------------------------------------ TEAM
    // ------------------------------------
    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public void updateTeam(Team team) {
        this.team = team;
    }

    public void deleteTeam() {
        this.team = null;
    }

    // ------------------------------------ USER
    // ------------------------------------

     public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }
    

    // ------------------------------------------------------------------------
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

    public double getshootsPerMatch() {
        shootsPerMatch = (totalShoots / totalMatches);
        return shootsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public double getGoalsPerMatch() {
        goalsPerMatch = (totalGoals / totalMatches);
        return goalsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public double getScoreAvg() {
        scoreAvg = (totalGoals / totalShoots);
        return scoreAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.
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

    public double getDuelAvg() {
        duelAvg = (wonDuels / duels);
        return duelAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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

    public double getPassesPerMatch() {
        passesPerMatch = (passes / totalMatches);
        return passesPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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

    public double getPassesAvg() {
        passesAvg = (goodPasses / passes);
        return passesAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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
