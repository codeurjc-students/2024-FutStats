package com.tfg.futstats.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.player.PlayerMatchDTO;

import jakarta.persistence.Column;
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

    // playerMatch info
    private String name;

    @Column(unique = true)
    private String matchName;

    // Realtions with other models in DB
    @ManyToOne
    @JsonIgnore
    private Player player;

    @ManyToOne
    @JsonIgnore
    private Match match;

    // player stats
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

    //Goalkeeper
    private int shootsReceived;
    private int goalsConceded;
    private int saves;
    private int outBoxSaves;
    private int inBoxSaves;

    // Constructors
    public PlayerMatch() {
    }

    public PlayerMatch(
            String name,
            String matchName,
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
            int ballLosses,
            int shootsReceived,
            int goalsConceded,
            int saves,
            int outBoxSaves,
            int inBoxSaves) {
        this.name = name;
        this.matchName = matchName;
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
        this.shootsReceived = shootsReceived;
        this.goalsConceded = goalsConceded;
        this.saves = saves;
        this.outBoxSaves = outBoxSaves;
        this.inBoxSaves = inBoxSaves;
    }

    public PlayerMatch(PlayerMatchDTO player) {
        this.name = player.getName();
        this.matchName = player.getMatchName();
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
        this.shootsReceived = player.getShootsReceived();
        this.goalsConceded = player.getGoalsConceded();
        this.saves = player.getSaves();
        this.outBoxSaves = player.getOutBoxSaves();
        this.inBoxSaves = player.getInBoxSaves();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getShoots() {
        return shoots;
    }

    public void setShoots(int shoots) {
        this.shoots = shoots;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
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

    public int getShootsReceived(){
        return shootsReceived;
    }

    public void setShootsReceived(int shootsReceived){
        this.shootsReceived = shootsReceived;
    }

    public int getGoalsConceded(){
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded){
        this.goalsConceded = goalsConceded;
    }

    public int getSaves(){
        return saves;
    }

    public void setSaves(int saves){
        this.saves = saves;
    }

    public int getOutBoxSaves(){
        return outBoxSaves;
    }

    public void setOutBoxSaves(int outBoxSaves){
        this.outBoxSaves = outBoxSaves;
    }

    public int getInBoxSaves(){
        return inBoxSaves;
    }

    public void setInBoxSaves(int inBoxSaves){
        this.inBoxSaves = inBoxSaves;
    }
}
