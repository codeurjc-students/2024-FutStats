package com.tfg.futstats.models;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.MatchDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
@Table(name = "`match`")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // Realtions with other models in DB
    @ManyToOne
    @JsonIgnore
    private League league;

    @ManyToOne
    @JsonIgnore
    private Team team1;

    @ManyToOne
    @JsonIgnore
    private Team team2;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerMatch> playerMatches;

    // Match attributes
    private Date date;
    private String place;

    // Team1 attributes
    private int shoots1;
    private int scores1;
    private int penaltys1;
    private int faultsReceived1;
    private int offsides1;
    private int commitedFaults1;
    private int recovers1;
    private int duels1;
    private int wonDuels1;
    private int yellowCards1;
    private int redCards1;
    private double possesion1;
    private int passes1;
    private int goodPasses1;
    private int shortPasses1;
    private int longPasses1;
    private int assists1;
    private int dribles1;
    private int centers1;
    private int ballLosses1;

    // Team2 attributes
    private int shoots2;
    private int scores2;
    private int penaltys2;
    private int faultsReceived2;
    private int offsides2;
    private int commitedFaults2;
    private int recovers2;
    private int duels2;
    private int wonDuels2;
    private int yellowCards2;
    private int redCards2;
    private double possesion2;
    private int passes2;
    private int goodPasses2;
    private int shortPasses2;
    private int longPasses2;
    private int assists2;
    private int dribles2;
    private int centers2;
    private int ballLosses2;

    // Constructors
    public Match() {
    }

    public Match(League league,
            Team team1,
            Team team2,
            Date date,
            String place,
            int shoots1,
            int scores1,
            int penaltys1,
            int faultsReceived1,
            int offsides1,
            int commitedFaults1,
            int recovers1,
            int duels1,
            int wonDuels1,
            int yellowCards1,
            int redCards1,
            double possesion1,
            int passes1,
            int goodPasses1,
            int shortPasses1,
            int longPasses1,
            int assists1,
            int dribles1,
            int centers1,
            int ballLosses1,
            int shoots2,
            int scores2,
            int penaltys2,
            int faultsReceived2,
            int offsides2,
            int commitedFaults2,
            int recovers2,
            int duels2,
            int wonDuels2,
            int yellowCards2,
            int redCards2,
            double possesion2,
            int passes2,
            int goodPasses2,
            int shortPasses2,
            int longPasses2,
            int assists2,
            int dribles2,
            int centers2,
            int ballLosses2) {
            this.league = league;
            this.team1 = team1;
            this.team2 = team2;
            this.date = date;
            this.place = place;
            this.shoots1 = shoots1;
            this.scores1 = scores1;
            this.penaltys1 = penaltys1;
            this.faultsReceived1 = faultsReceived1;
            this.offsides1 = offsides1;
            this.commitedFaults1 = commitedFaults1;
            this.recovers1 = recovers1;
            this.duels1 = duels1;
            this.wonDuels1 = wonDuels1;
            this.yellowCards1 = yellowCards1;
            this.redCards1 = redCards1;
            this.possesion1 = possesion1;
            this.passes1 = passes1;
            this.goodPasses1 = goodPasses1;
            this.shortPasses1 = shortPasses1;
            this.longPasses1 = longPasses1;
            this.assists1 = assists1;
            this.dribles1 = dribles1;
            this.centers1 = centers1;
            this.ballLosses1 = ballLosses1;
            this.shoots2 = shoots2;
            this.scores2 = scores2;
            this.penaltys2 = penaltys2;
            this.faultsReceived2 = faultsReceived2;
            this.offsides2 = offsides2;
            this.commitedFaults2 = commitedFaults2;
            this.recovers2 = recovers2;
            this.duels2 = duels2;
            this.wonDuels2 = wonDuels2;
            this.yellowCards2 = yellowCards2;
            this.redCards2 = redCards2;
            this.possesion2 = possesion2;
            this.passes2 = passes2;
            this.goodPasses2 = goodPasses2;
            this.shortPasses2 = shortPasses2;
            this.longPasses2 = longPasses2;
            this.assists2 = assists2;
            this.dribles2 = dribles2;
            this.centers2 = centers2;
            this.ballLosses2 = ballLosses2;
    }

    public Match(MatchDTO match) {
        this.date = match.getDate();
        this.place = match.getPlace();
        this.shoots1 = match.getShoots1();
        this.scores1 = match.getScores1();
        this.penaltys1 = match.getPenaltys1();
        this.faultsReceived1 = match.getFaultsReceived1();
        this.offsides1 = match.getOffsides1();
        this.commitedFaults1 = match.getCommitedFaults1();
        this.recovers1 = match.getRecovers1();
        this.duels1 = match.getDuels1();
        this.wonDuels1 = match.getWonDuels1();
        this.yellowCards1 = match.getYellowCards1();
        this.redCards1 = match.getRedCards1();
        this.possesion1 = match.getPossesion1();
        this.passes1 = match.getPasses1();
        this.goodPasses1 = match.getGoodPasses1();
        this.shortPasses1 = match.getShortPasses1();
        this.longPasses1 = match.getLongPasses1();
        this.assists1 = match.getAssists1();
        this.dribles1 = match.getDribles1();
        this.centers1 = match.getCenters1();
        this.ballLosses1 = match.getBallLosses1();
        this.shoots2 = match.getShoots2();
        this.scores2 = match.getScores2();
        this.penaltys2 = match.getPenaltys2();
        this.faultsReceived2 = match.getFaultsReceived2();
        this.offsides2 = match.getOffsides2();
        this.commitedFaults2 = match.getCommitedFaults2();
        this.recovers2 = match.getRecovers2();
        this.duels2 = match.getDuels2();
        this.wonDuels2 = match.getWonDuels2();
        this.yellowCards2 = match.getYellowCards2();
        this.redCards2 = match.getRedCards2();
        this.possesion2 = match.getPossesion2();
        this.passes2 = match.getPasses2();
        this.goodPasses2 = match.getGoodPasses2();
        this.shortPasses2 = match.getShortPasses2();
        this.longPasses2 = match.getLongPasses2();
        this.assists2 = match.getAssists2();
        this.dribles2 = match.getDribles2();
        this.centers2 = match.getCenters2();
        this.ballLosses2 = match.getBallLosses2();
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
    // league doesn´t need delete or update because it cannot be change

    // ------------------------------------ TEAM1
    // -------------------------------------
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam1() {
        return team1;
    }

    // team1 doesn`t need delete or update because there can´t be only one team in a
    // match and there can´t be team change in a single match
    // ------------------------------------ TEAM2
    // -------------------------------------
    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Team getTeam2() {
        return this.team2;
    }


    // team2 doesn`t need delete or update because there can´t be only one team in a
    // match and there can´t be team change in a single match

    // ------------------------------------ PLAYER MATCH   
    // ------------------------------------

    public void setPlayerMatch(PlayerMatch playerMatch) {
        this.playerMatches.add(playerMatch);
    }

    public List<PlayerMatch> getPlayerMatches() {
        return this.playerMatches;
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


    // ------------------------------------------------------------------------
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getShoots1() {
        return shoots1;
    }

    public void setShoots1(int shoots1) {
        this.shoots1 = shoots1;
    }

    public int getScores1() {
        return scores1;
    }

    public void setScores1(int scores1) {
        this.scores1 = scores1;
    }

    public int getPenaltys1()
    {
        return penaltys1;
    }

    public void setPenaltys1(int penaltys)
    {
        this.penaltys1 = penaltys;
    }

    public int getFaultsReceived1()
    {
        return faultsReceived1;
    }

    public void setFaultsReceived1(int faultsReceived)
    {
        this.faultsReceived1 = faultsReceived;
    }

    public int getOffsides1() {
        return offsides1;
    }

    public void setOffsides1(int offsides1) {
        this.offsides1 = offsides1;
    }

    public int getCommitedFaults1()
    {
        return commitedFaults1;
    }

    public void setCommitedFaults1(int commitedFaults)
    {
        this.commitedFaults1 = commitedFaults;
    }

    public int getRecovers1()
    {
        return recovers1;
    }

    public void setRecovers1(int recovers)
    {
        this.recovers1 = recovers;
    }

    public int getDuels1() {
        return duels1;
    }

    public void setDuels1(int duels) {
        this.duels1 = duels;
    }

    public int getWonDuels1() {
        return wonDuels1;
    }

    public void setWonDuels1(int wonDuels) {
        this.wonDuels1 = wonDuels;
    }

    public int getYellowCards1() {
        return yellowCards1;
    }

    public void setYellowCards1(int yellowCards1) {
        this.yellowCards1 = yellowCards1;
    }

    public int getRedCards1() {
        return redCards1;
    }

    public void setRedCards1(int redCards1) {
        this.redCards1 = redCards1;
    }

    public double getPossesion1() {
        return possesion1;
    }

    public void setPossesion1(double possesion1) {
        this.possesion1 = possesion1;
    }

    public int getPasses1() {
        return passes1;
    }

    public void setPasses1(int passes1) {
        this.passes1 = passes1;
    }

    public int getGoodPasses1() {
        return goodPasses1;
    }

    public void setGoodPasses1(int goodPasses1) {
        this.goodPasses1 = goodPasses1;
    }

    public int getShortPasses1() {
        return shortPasses1;
    }

    public void setShortPasses1(int shortPasses1) {
        this.shortPasses1 = shortPasses1;
    }

    public int getLongPasses1() {
        return longPasses1;
    }

    public void setLongPasses1(int longPasses1) {
        this.longPasses1 = longPasses1;
    }

    public int getAssists1() {
        return assists1;
    }

    public void setAssists1(int assists1) {
        this.assists1 = assists1;
    }

    public int getDribles1() {
        return dribles1;
    }

    public void setDribles1(int dribles1) {
        this.dribles1 = dribles1;
    }

    public int getCenters1() {
        return centers1;
    }

    public void setCenters1(int centers1) {
        this.centers1 = centers1;
    }

    public int getBallLosses1() {
        return ballLosses1;
    }

    public void setBallLosses1(int ballLosses1) {
        this.ballLosses1 = ballLosses1;
    }

    public int getShoots2() {
        return shoots2;
    }

    public void setShoots2(int shoots1) {
        this.shoots2 = shoots1;
    }

    public int getScores2() {
        return scores2;
    }

    public void setScores2(int scores1) {
        this.scores2 = scores1;
    }

    public int getPenaltys2()
    {
        return penaltys2;
    }

    public void setPenaltys2(int penaltys)
    {
        this.penaltys2 = penaltys;
    }

    public int getFaultsReceived2()
    {
        return faultsReceived2;
    }

    public void setFaultsReceived2(int faultsReceived)
    {
        this.faultsReceived2 = faultsReceived;
    }

    public int getOffsides2() {
        return offsides2;
    }

    public void setOffsides2(int offsides1) {
        this.offsides2 = offsides1;
    }

    public int getCommitedFaults2()
    {
        return commitedFaults2;
    }

    public void setCommitedFaults2(int commitedFaults)
    {
        this.commitedFaults2 = commitedFaults;
    }

    public int getRecovers2()
    {
        return recovers2;
    }

    public void setRecovers2(int recovers)
    {
        this.recovers2 = recovers;
    }

    public int getDuels2() {
        return duels2;
    }

    public void setDuels2(int duels) {
        this.duels2 = duels;
    }

    public int getWonDuels2() {
        return wonDuels2;
    }

    public void setWonDuels2(int wonDuels) {
        this.wonDuels2 = wonDuels;
    }

    public int getYellowCards2() {
        return yellowCards2;
    }

    public void setYellowCards2(int yellowCards) {
        this.yellowCards2 = yellowCards;
    }

    public int getRedCards2() {
        return redCards2;
    }

    public void setRedCards2(int redCards) {
        this.redCards2 = redCards;
    }

    public double getPossesion2() {
        return possesion2;
    }

    public void setPossesion2(double possesion) {
        this.possesion2 = possesion;
    }

    public int getPasses2() {
        return passes2;
    }

    public void setPasses2(int passes) {
        this.passes2 = passes;
    }

    public int getGoodPasses2() {
        return goodPasses2;
    }

    public void setGoodPasses2(int goodPasses) {
        this.goodPasses2 = goodPasses;
    }

    public int getShortPasses2() {
        return shortPasses2;
    }

    public void setShortPasses2(int shortPasses2) {
        this.shortPasses2 = shortPasses2;
    }

    public int getLongPasses2() {
        return longPasses2;
    }

    public void setLongPasses2(int longPasses2) {
        this.longPasses2 = longPasses2;
    }

    public int getAssists2() {
        return assists2;
    }

    public void setAssists2(int assists2) {
        this.assists2 = assists2;
    }

    public int getDribles2() {
        return dribles2;
    }

    public void setDribles2(int dribles2) {
        this.dribles2 = dribles2;
    }

    public int getCenters2() {
        return centers2;
    }

    public void setCenters2(int centers2) {
        this.centers2 = centers2;
    }

    public int getBallLosses2() {
        return ballLosses2;
    }

    public void setBallLosses2(int ballLosses2) {
        this.ballLosses2 = ballLosses2;
    }
}
