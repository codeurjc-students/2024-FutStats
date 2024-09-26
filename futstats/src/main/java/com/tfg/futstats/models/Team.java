package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.tfg.futstats.controllers.DTOs.TeamDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    // Realtions with other models in DB
    @ManyToOne
    private League league;

    @OneToMany
    private List<Match> matches;

    @OneToMany
    private List<Player> players;

    @ManyToMany
    private List<User> users;

    // Team attributes
    private String name;
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
    private float duelAvg;
    private int cards;
    private int yellowCards;
    private int redCards;

    // creation
    private double possesionPerMatch;
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

    // matches
    private int playedMatches;
    private int wonMatches;
    private int lostMatches;
    private int drawMatches;
    private float wonMatchesAvg;

    // Constructors
    public Team() {

    }

    public Team(League league,
            String name,
            int trophies,
            String nationality,
            String trainer,
            String secondTrainer,
            String president,
            String stadium,
            int points,
            int totalShoots,
            int totalGoals,
            int penaltys,
            int faultsReceived,
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
            int ballLosses,
            int playedMatches,
            int wonMatches,
            int lostMatches,
            int drawMatches) {
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.users = new ArrayList<User>();
        this.league = league;
        this.name = name;
        this.trophies = trophies;
        this.nationality = nationality;
        this.trainer = trainer;
        this.secondTrainer = secondTrainer;
        this.president = president;
        this.stadium = stadium;
        this.points = points;
        this.totalShoots = totalShoots;
        this.totalGoals = totalGoals;
        this.penaltys = penaltys;
        this.faultsReceived = faultsReceived;
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
        this.playedMatches = playedMatches;
        this.wonMatches = wonMatches;
        this.lostMatches = lostMatches;
        this.drawMatches = drawMatches;
    }

    public Team(TeamDTO team) {
        this.name = team.getName();
        this.trophies = team.getTrophies();
        this.nationality = team.getNationality();
        this.trainer = team.getTrainer();
        this.secondTrainer = team.getSecondTrainer();
        this.president = team.getPresident();
        this.stadium = team.getStadium();
        this.points = team.getPoints();
        this.totalShoots = team.getTotalShoots();
        this.totalGoals = team.getTotalGoals();
        this.penaltys = team.getPenaltys();
        this.faultsReceived = team.getFaultsReceived();
        this.offsides = team.getOffsides();
        this.commitedFaults = team.getCommitedFaults();
        this.recovers = team.getRecovers();
        this.duels = team.getDuels();
        this.wonDuels = team.getWonDuels();
        this.lostDuels = team.getLostDuels();
        this.cards = team.getCards();
        this.yellowCards = team.getYellowCards();
        this.redCards = team.getRedCards();
        this.passes = team.getPasses();
        this.goodPasses = team.getGoodPasses();
        this.badPasses = team.getBadPasses();
        this.shortPasses = team.getShortPasses();
        this.longPasses = team.getLongPasses();
        this.assists = team.getAssists();
        this.dribles = team.getDribles();
        this.centers = team.getCenters();
        this.ballLosses = team.getBallLosses();
        this.playedMatches = team.getPlayedMatches();
        this.wonMatches = team.getWonMatches();
        this.lostMatches = team.getLostMatches();
        this.drawMatches = team.getDrawMatches();
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

    // --------------------------------------- MATCH
    // ---------------------------------
    public List<Match> getMatches() {
        return this.matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void setMatch(Match match) {
        this.matches.add(match);
        match.setLeague(this);
    }

    public void deleteMatch(Match match) {
        this.matches.remove(match);
    }

    // --------------------------------------- PLAYER
    // --------------------------------
    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayers(Player player) {
        this.players.add(player);
        player.setLeague(this);
    }

    public void deletePlayer(Player player) {
        this.players.remove(player);
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

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setSecondTrainer(String secondTrainer) {
        this.secondTrainer = secondTrainer;
    }

    public String getSecondTrainer() {
        return secondTrainer;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getPresident() {
        return president;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getStadium() {
        return stadium;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
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

    public double getShootsPerMatch() {
        shootsPerMatch = (totalShoots / totalMatches);
        return shootsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public double getGoalsPerMatch()
    {
        goalsPerMatch = (totalGoals/totalMatches)
        return goalsPerMatch;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public double getScoreAvg()
    {
        scoreAvg = (totalGoals/totalShoots)
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

    public void setFaultsReceived(int fautlsReceived) {
        this.fautlsReceived = fautlsReceived;
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

    public double getDuelAvg()
    {
        duelAvg = (wonDuels/duels)
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

    // PossesionPerMatch

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
        this.badPases = badPasses;
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

    public void setPlayedMatches(int playedMatches) {
        this.playedMatches = playedMatches;
    }

    public int getPlayedMatches() {
        return playedMatches;
    }

    public void setWonMatches(int wonMatches) {
        this.wonMatches = wonMatches;
    }

    public int getWonMatches() {
        return wonMatches;
    }

    public void setLostMatches(int lostMatches) {
        this.lostMatches = lostMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public void setDrawMatches(int drawMatches) {
        this.drawMatches = drawMatches;
    }

    public int getDrawMatches() {
        return drawMatches;
    }

    public int getWonMatchesAvg() {
        wonMatchesAvg = (wonMatches / totalMatches)
        return wonMatchesAvg;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

}
