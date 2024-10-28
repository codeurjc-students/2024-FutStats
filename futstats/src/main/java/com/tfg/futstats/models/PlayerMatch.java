package com.tfg.futstats.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.PlayerMatchDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PlayerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Player player;

    @ManyToOne
    @JsonIgnore
    private Match match;

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

    // Constructors
    public PlayerMatch() {
    }

    public PlayerMatch(
            int shoots,
            int goals,
            int penaltys,
            int fautlsReceived,
            int offsides,
            int commitedFaults,
            int recovers,
            int duels,
            int wonDuels,
            int lostDuels,
            int cards,
            int yellowCards,
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
        this.shoots = shoots;
        this.goals = goals;
        this.penaltys = penaltys;
        this.faultsReceived = fautlsReceived;
        this.offsides = offsides;
        this.commitedFaults = commitedFaults;
        this.recovers = recovers;
        this.duels = duels;
        this.wonDuels = wonDuels;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.passes = passes;
        this.goodPasses = goodPasses;
        this.shortPasses = shortPasses;
        this.longPasses = longPasses;
        this.assists = assists;
        this.dribles = dribles;
        this.centers = centers;
        this.ballLosses = ballLosses;
    }

    public PlayerMatch(PlayerMatchDTO player) {
        this.shoots = player.getShoots();
        this.goals = player.getGoals();
        this.penaltys = player.getPenaltys();
        this.faultsReceived = player.getFaultsReceived();
        this.offsides = player.getOffsides();
        this.commitedFaults = player.getCommitedFaults();
        this.recovers = player.getRecovers();
        this.duels = player.getDuels();
        this.wonDuels = player.getWonDuels();
        this.yellowCards = player.getYellowCards();
        this.redCards = player.getRedCards();
        this.passes = player.getPasses();
        this.goodPasses = player.getGoodPasses();
        this.shortPasses = player.getShortPasses();
        this.longPasses = player.getLongPasses();
        this.assists = player.getAssists();
        this.dribles = player.getDribles();
        this.centers = player.getCenters();
        this.ballLosses = player.getBallLosses();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setShoots(int shoots) {
        this.shoots = shoots;
    }

    public int getShoots() {
        return shoots;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getlGoals() {
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
