package com.tfg.futstats.models;

import java.sql.Date;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.tfg.futstats.controllers.DTOs.MatchDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    // Realtions with other models in DB
    @ManyToOne
    private League league;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    // Match attributes
    private Date date;
    private String place;

    // Team1 attributes
    private int shoots1;
    private int scores1;
    private double possesion1;
    private int passes1;
    private int goodPasses1;
    private double passesAvg1;
    private int faults1;
    private int yellowCards1;
    private int redCards1;
    private int offsides1;

    // Team2 attributes
    private int shoots2;
    private int scores2;
    private double possesion2;
    private int passes2;
    private int goodPasses2;
    private double passesAvg2;
    private int faults2;
    private int yellowCards2;
    private int redCards2;
    private int offsides2;

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
            double possesion1,
            int passes1,
            int goodPasses1,
            int faults1,
            int yellowCards1,
            int redCards1,
            int offsides1,
            int shoots2,
            int scores2,
            double possesion2,
            int passes2,
            int goodPasses2,
            int faults2,
            int yellowCards2,
            int redCards2,
            int offsides2) {
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.place = place;
        this.shoots1 = shoots1;
        this.scores1 = scores1;
        this.possesion1 = possesion1;
        this.passes1 = passes1;
        this.goodPasses1 = goodPasses1;
        this.faults1 = faults1;
        this.yellowCards1 = yellowCards1;
        this.redCards1 = redCards1;
        this.offsides1 = offsides1;
        this.shoots2 = shoots2;
        this.scores2 = scores2;
        this.possesion2 = possesion2;
        this.passes2 = passes2;
        this.goodPasses2 = goodPasses2;
        this.faults2 = faults2;
        this.yellowCards2 = yellowCards2;
        this.redCards2 = redCards2;
        this.offsides2 = offsides2;
    }

    public Match(MatchDTO match) {
        this.league = match.getLeague();
        this.team1 = match.getTeam1();
        this.team2 = match.getTeam2();
        this.date = match.getDate();
        this.place = match.getPlace();
        this.shoots1 = match.getShoots1();
        this.scores1 = match.getScores1();
        this.possesion1 = match.getPossesion1();
        this.passes1 = match.getPasses1();
        this.goodPasses1 = match.getGoodPasses1();
        this.faults1 = match.getFaults1();
        this.yellowCards1 = match.getYellowCards1();
        this.redCards1 = match.getRedCards1();
        this.offsides1 = match.getOffsides1();
        this.shoots2 = match.getShoots2();
        this.scores2 = match.getScoress2();
        this.possesion2 = match.getPossesion2();
        this.passes2 = match.getPasses2();
        this.goodPasses2 = match.getGoodPasses2();
        this.faults2 = match.getFaults2();
        this.yellowCards2 = match.getYellowCards2();
        this.redCards2 = match.getRedCards2();
        this.offsides2 = match.getOffsides2();
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

    public String getTeam1Name() {
        return team1.getName();
    }

    public String getTeam1Stadium() {
        return team1.getStadium();
    }

    public List<Player> getTeam1Players() {
        return team1.getPlayers();
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

    public String getTeam2Name() {
        return team2.getName();
    }

    public String getTeam2Stadium() {
        return team2.getStadium();
    }

    public List<Player> getTeam2Players() {
        return team2.getPlayers();
    }

    // team2 doesn`t need delete or update because there can´t be only one team in a
    // match and there can´t be team change in a single match
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

    public int getShoots1()
    {
        return shoots1;
    }

    public void setShoots1(int shoots1)
    {
        this.shoots1 = shoots1;
    }

    public int getScoress1()
    {
        return scores1;
    }

    public void setScores1(int scores1)
    {
        this.scores1 = scores1;
    }

    public double getPossesion1()
    {
        return possesion1;
    }

    public void setPossesion1(double possesion1)
    {
        this.possesion1 = possesion1
    }

    public int getPasses1()
    {
        return passes1;
    }

    public void setPasses1(int passes1)
    {
        this.passes1 = passes1;
    }

    public int getGoodPasses1()
    {
        return goodPasses1;
    }

    public void setGoodPasses1(int goodPasses1)
    {
        this.goodPasses1 = goodPasses1;
    }

    public double getPassesAvg1()
    {
        passesAvg1 = (goodPasses1/passes1);
        return passesAvg1;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public int getFaults1()
    {
        return faults1;
    }

    public void setFaults1(int faults1)
    {
        this.faults1 = faults1;
    }
    
    public int getYellowCards1()
    {
        return yellowCards1;
    }

    public void setYellowCards1(int yellowCards1)
    {
        return yellowCards1;
    }

    public int getRedCards1()
    {
        return redCards1;
    }

    public void setRedCards1(int redCards1)
    {
        this.redCards1 = redCards1;
    }

    public int getOffsides1()
    {
        return offsides1;
    }

    public void setOffsides1(int offsides1)
    {
        this.offsides1 = offsides1;
    }

    public int getShoots2()
    {
        return shoots2;
    }

    public void setShoots2(int shoots2)
    {
        this.shoots2 = shoots2;
    }

    public int getScoress2()
    {
        return scores2;
    }

    public void setScores2(int scores2)
    {
        this.scores2 = scores2;
    }

    public double getPossesion2()
    {
        return possesion2;
    }

    public void setPossesion2(double possesion2)
    {
        this.possesion2 = possesion2;
    }

    public int getPasses2()
    {
        return passes2;
    }

    public void setPasses2(int passes2)
    {
        this.passes2 = passes2;
    }

    public int getGoodPasses2()
    {
        return goodPasses2;
    }

    public void setGoodPasses2(int goodPasses2)
    {
        this.goodPasses2 = goodPasses2;
    }

    public double getPassesAvg2()
    {
        passesAvg2 = (goodPasses2/passes2);
        return passesAvg2;
    }

    // As it is an average it only needs a getter it doesn´t need to be setted because
    // it`s calculated from other stadistics.

    public int getFaults2()
    {
        return faults2;
    }

    public void setFaults2(int faults2)
    {
        this.faults2 = faults2;
    }
    
    public int getYellowCards2()
    {
        return yellowCards2;
    }

    public void setYellowCards2(int yellowCards2)
    {
        return yellowCards2;
    }

    public int getRedCards2()
    {
        return redCards2;
    }

    public void setRedCards2(int redCards2)
    {
        this.redCards2 = redCards2;
    }

    public int getOffsides2()
    {
        return offsides2;
    }

    public void setOffsides2(int offsides2)
    {
        this.offsides2 = offsides2;
    }
}
