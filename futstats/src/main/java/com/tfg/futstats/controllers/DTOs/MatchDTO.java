package com.tfg.futstats.controllers.dtos;

import java.sql.Date;

import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Team;

public class MatchDTO {
    // dto attributes
    private long id;
    private League league;
    private Team team1;
    private Team team2;
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

    // We dont put the averages here because we only need the stadistics from were
    // it`s made

    // Getters & setters
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public League getLeague() {
        return this.league;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
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

    // --------------------------------------- TEAM 2
    // --------------------------------

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
