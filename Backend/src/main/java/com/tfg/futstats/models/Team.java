package com.tfg.futstats.models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.team.TeamCreationDTO;
import com.tfg.futstats.controllers.dtos.team.TeamUpdateDTO;

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
import jakarta.validation.constraints.NotNull;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // Team attributes
     @Column(unique = true)
     @NotNull
    private String name;
    private int trophies;
    private String nationality;
    private String trainer;
    private String secondTrainer;
    private String president;
    private String stadium;
    private int points;

    @Lob
	@JsonIgnore
	private Blob imageFile;

	private boolean image;

    // Realtions with other models in DB
    @ManyToOne
    @JsonIgnore
    private League league;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<Match> matches;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Player> players; 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamMatch> teamMatches;

    @ManyToMany(mappedBy = "belongedTeams")
    @JsonIgnore
    private List<User> users;
    
    // team stats
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
    private int yellowCards;
    private int redCards;
    private int cards;

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

    // matches
    private int wonMatches;
    private int lostMatches;
    private int drawMatches;
    private float wonMatchesAvg;

    // Constructors
    public Team() {
        //this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.teamMatches = new ArrayList<TeamMatch>();
    }

    public Team(League league,
            String name,
            int trophies,
            String nationality,
            String trainer,
            String secondTrainer,
            String president,
            String stadium,
            Blob imageFile,
            boolean image
            ) {
        //this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.teamMatches = new ArrayList<TeamMatch>();
        this.league = league;
        this.name = name;
        this.trophies = trophies;
        this.nationality = nationality;
        this.trainer = trainer;
        this.secondTrainer = secondTrainer;
        this.president = president;
        this.stadium = stadium;
        this.image = image;
        this.imageFile = imageFile;
        
    }

    public Team(TeamCreationDTO team) {
        this.name = team.getName();
        this.trophies = team.getTrophies();
        this.nationality = team.getNationality();
        this.trainer = team.getTrainer();
        this.secondTrainer = team.getSecondTrainer();
        this.president = team.getPresident();
        this.points = team.getWonMatches() * 3;
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

    public Team(TeamUpdateDTO team) {
        this.name = team.getName();
        this.trophies = team.getTrophies();
        this.nationality = team.getNationality();
        this.trainer = team.getTrainer();
        this.secondTrainer = team.getSecondTrainer();
        this.president = team.getPresident();
        this.stadium = team.getStadium();
        this.image = team.getImage();
    }

    // Getters & Setters
    // ------------------------------------ LEAGUE
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

    // --------------------------------------- MATCH
    // public List<Match> getMatches() {
    //     return this.matches;
    // }

    // public void setMatches(List<Match> matches) {
    //     this.matches = matches;
    // }

    // public void setMatch(Match match) {
    //     this.matches.add(match);
    //     match.setLeague(this.league);
    // }

    // public void deleteMatch(Match match) {
    //     this.matches.remove(match);
    // }

    // --------------------------------------- PLAYER
    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
        player.setLeague(this.league);
    }

    public void deletePlayer(Player player) {
        this.players.remove(player);
    }

    // ------------------------------------ TEAM MATCH
    public List<TeamMatch> getTeamMatches() {
        return this.teamMatches;
    }

    public void setTeamMatch(TeamMatch teamMatch) {
        this.teamMatches.add(teamMatch);
    }

    public void setTeamMatches(List<TeamMatch> teamMatches) {
        this.teamMatches = teamMatches;
    }

    public void deleteTeamMatch(TeamMatch teamMatch) {
        this.teamMatches.remove(teamMatch);
    }

    public void deleteTeamMatches() {
        this.teamMatches = null;
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
        user.setTeam(this);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    // ------------------------------------------------------------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (this.wonMatches == 0) {
            if(this.drawMatches == 0){
                return 0;
            }
        }
        points = wonMatches * 3 + drawMatches * 1;
        return  points;
    }

    public void setPoints(int wonMatches) {
        this.points = wonMatches * 3;
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

    public double getShootsPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        shootsPerMatch = (float) (totalShoots / totalMatches);
        return shootsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

    public double getGoalsPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        goalsPerMatch = (float) (totalGoals / totalMatches);
        return goalsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

    public double getScoreAvg() {
        if (totalShoots == 0) {
            return 0;
        }
        scoreAvg = (float) (totalGoals / totalShoots);
        return scoreAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
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

    public double getDuelAvg() {
        if (duels == 0) {
            return 0;
        }
        duelAvg = (float) (wonDuels / duels);
        return duelAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

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

    public int getCards() {
        cards = redCards + yellowCards;
        return cards;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }

    public double getPassesPerMatch() {
        if (totalMatches == 0) {
            return 0;
        }
        passesPerMatch = (float) (passes / totalMatches);
        return passesPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

    public int getGoodPasses() {
        return goodPasses;
    }

    public void setGoodPasses(int goodPasses) {
        this.goodPasses = goodPasses;
    }

    public double getPassesAvg() {
        if (passes == 0) {
            return 0;
        }
        passesAvg = (float) (goodPasses / passes);
        return passesAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
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

    public float getWonMatchesAvg() {
        if (totalMatches == 0) {
            return 0;
        }
        wonMatchesAvg = (float) (wonMatches / totalMatches);
        return wonMatchesAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted
    // because
    // it`s calculated from other stadistics.

}
