package com.tfg.futstats.models;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.player.PlayerDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // player info
     @Column(unique = true)
    private String name;
    private int age;
    private String nationality;
    private String position;

    @Lob
	@JsonIgnore
	private Blob imageFile;

	private boolean image;

    // Realtions with other models in DB
    @ManyToOne
    @JsonIgnore
    private League league;

    @ManyToOne
    @JsonIgnore
    private Team team;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerMatch> playerMatches;

    @ManyToMany(mappedBy = "belongedPlayers")
    private List<User> users;

    // player stats
    private int totalMatches;

    // offensive
    private int totalShoots;
    private int totalGoals;
    private float shootsPerMatch;
    private float goalsPerMatch;
    private float scoreAvg;
    private int penaltys;
    private int faultsReceived;
    private int offsides;

    // defensive
    private int commitedFaults;
    private int recovers;
    private int duels;
    private int wonDuels;
    private float duelAvg;
    private int cards;
    private int yellowCards;
    private int redCards;

    // creation
    private int passes;
    private float passesPerMatch;
    private int goodPasses;
    private float passesAvg;
    private int shortPasses;
    private int longPasses;
    private int assists;
    private int dribles;
    private int centers;
    private int ballLosses;

    //Goalkeeper
    private int shootsReceived;
    private int goalsConceded;
    private float goalsReceivedPerMatch;
    private int saves;
    private float savesAvg;
    private int outBoxSaves;
    private int inBoxSaves;

    // Constructors
    public Player() {

    }

    public Player(League league,
            Team team,
            String name,
            int age,
            String nationality,
            String position,
            Blob imageFile,
            boolean image
            ) {
        this.league = league;
        this.team = team;
        this.name = name;
        this.age = age;
        this.imageFile = imageFile;
        this.image = image;
        this.nationality = nationality;
        this.position = position;
    }

    public Player(PlayerDTO player) {
        this.name = player.getName();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.position = player.getPosition();
        this.image = player.getImage();
    }

    // Getters & Setters
    // ------------------------------------ LEAGUE
    // -------------------------------------
    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void updateLeague(League league) {
        this.league = league;
    }

    public void deleteLeague() {
        this.league = null;
    }

    // ------------------------------------ TEAM
    // ------------------------------------
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void updateTeam(Team team) {
        this.team = team;
    }

    public void deleteTeam() {
        this.team = null;
    }

    // ------------------------------------ PLAYER MATCH   
    // ------------------------------------
    public List<PlayerMatch> getPlayerMatches() {
        return this.playerMatches;
    }

    public void setPlayerMatch(PlayerMatch playerMatch) {
        this.playerMatches.add(playerMatch);
    }

    public void setPlayerMatches(List<PlayerMatch> playerMatches) {
        this.playerMatches = playerMatches;
    }

    public void deletePlayerMatch(PlayerMatch playerMatch){
        this.playerMatches.remove(playerMatch);
    }

    public void deletePlayerMatches() {
        this.playerMatches = null;
    }

    // --------------------------------------- USER 
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUser(User user) {
        this.users.add(user);
        user.setPlayer(this);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public Blob getImageFile() {
		return imageFile;
	}

	public void setImageFile(Blob image) {
		this.imageFile = image;
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

    public float getshootsPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        shootsPerMatch = (float) totalShoots / totalMatches;
        return shootsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public float getGoalsPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        goalsPerMatch = (float) totalGoals / totalMatches;
        return goalsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public float getScoreAvg() {
        if (totalShoots == 0) {
            return 0;
        }
        scoreAvg = (float) totalGoals / totalShoots;
        return scoreAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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

    public float getDuelAvg() {
        if (duels == 0) {
            return 0;
        }
        duelAvg = (float) wonDuels / duels;
        return duelAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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

    public float getPassesPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        passesPerMatch = (float) passes / totalMatches;
        return passesPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public int getGoodPasses() {
        return goodPasses;
    }

    public void setGoodPasses(int goodPasses) {
        this.goodPasses = goodPasses;
    }

    public float getPassesAvg() {
        if (passes == 0) {
            return 0;
        }
        passesAvg = (float) goodPasses / passes;
        return passesAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

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

    public void getShootsReceived(int shootsReceived){
        this.shootsReceived = shootsReceived;
    }

    public int getGoalsConceded(){
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded){
        this.goalsConceded = goalsConceded;
    }

    public double getGoalsReceivedPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        goalsReceivedPerMatch = (float) (goalsConceded / totalMatches);
        return goalsReceivedPerMatch;
    }

    public int getSaves(){
        return saves;
    }

    public void setSaves(int saves){
        this.saves = saves;
    }

    public float getSavesAvg(){
        if(shootsReceived == 0) {
            return 0;
        }
        savesAvg = (float) saves / shootsReceived;
        return savesAvg;
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
