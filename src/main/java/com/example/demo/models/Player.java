package com.example.demo.models;

import javax.management.InvalidAttributeValueException;

public class Player {

    //  player info
    private String name;
    private int age;
    private String nationality;

    //  player stats
    //  offensive
    private int totalShoots;
    private int goalShoots;
    private int totalGoals;
    private int penaltys;
    private float scoreAvg;
    private int fautlsReceived;
    private int offsides;

    //  defensive
    private int commitedFaults;
    private int recovers;
    private int duels;
    private int wonDuels;
    private int lostDuels;
    private int cards;
    private int yellowCards;
    private int redCards;

    //  creation
    private int passes;
    private int goodPases;
    private int shortPasses;
    private int longPasses;
    private int assists;
    private int dribles;
    private int centers;
    private int ballLosses;

    public Player(String name, int age, String nationality, int totalShoots, int goalShoots, int totalGoals, int penaltys, float scoreAvg,
     int fautlsReceived, int offsides, int commitedFaults, int recovers, int duels, int wonDuels, int lostDuels, int cards, int yellowCrads,
     int redCards, int passes, int goodPases, int shortPasses, int longPasses, int assists, int dribles, int centers, int ballLosses)
     {
        
        this.name = name;
        this.age = age;
        this.nationality = nationality
        this.totalShoots = totalShoots;
        this.goalShoots = goalShoots;
        this.totalGoals = totalGoals;
        this.penaltys = penaltys;
        this.scoreAvg = scoreAvg;
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
        this.goodPases = goodPases;
        this.shortPasses = shortPasses;
        this.longPasses = longPasses;
        this.assists = assists;
        this.dribles = dribles;
        this.centers = centers;
        this.ballLosses = ballLosses;
     }

     //Getters y Setters

     public void setName(String name)
     {
        this.name = name;
     }

     public String getName()
     {
        return name;    
     }

     public void setAge(int age)
     {
        this.age = age;
     }

     public String getAge()
     {
        return age;    
     }

     public void setNationality(String nationality)
     {
        this.nationality = nationality;
     }

     public String getNationality()
     {
        return nationality;
     }

     public void setTotalShoots(int totalShoots)
     {
        this.totalShoots = totalShoots;
     }

     public int getTotalShoots()
     {
        return totalShoots;
     }

     public void setGoalShoots(int goalShoots)
     {
        this.goalShoots = goalShoots;
     }

     public int getGoalShoots()
     {
        return goalShoots;
     }

     public void setTotalGoals(int totalGoals)
     {
        this.totalGoals = totalGoals;
     }

     public int getTotalGoals()
     {
        return totalGoals;
     }

     public void setPenaltys(int penaltys)
     {
        this.penaltys = penaltys;
     }

     public int getPenaltys()
     {
        return penaltys;
     }

     public void setScoreAvg(float scoreAvg)
     {
        this.scoreAvg = scoreAvg;
     }

     public float getScoreAvg()
     {
        return scoreAvg;
     }

     public void setFaultsReceived(int fautlsReceived)
     {
        this.fautlsReceived = fautlsReceived;
     }

     public int getFaultsReceived()
     {
        return fautlsReceived;
     }

     public void setOffsides(int offsides)
     {
        this.offsides = offsides;
     }

     public int getOffsides()
     {
        return offsides;
     }

     public void setCommitedFaults(int commitedFaults)
     {
        this.commitedFaults = commitedFaults;
     }

     public int getCommitedFaults()
     {
        return commitedFaults;
     }

     public void setRecovers(int recovers)
     {
        this.recovers = recovers;
     }

     public int getRecovers()
     {
        return recovers;
     }

     public void setDuels(int duels)
     {
        this.duels = duels;
     }

     public int getDuels()
     {
        return duels;
     }
 
     public void setWonDuels(int wonDuels)
     {
        this.wonDuels = wonDuels;
     }

     public int getWonDuels()
     {
        return wonDuels;
     }

     public void setLostDuels(int lostDuels)
     {
        this.lostDuels = lostDuels;
     }

     public int getLostDuels()
     {
        return lostDuels;
     }

     public void setCards(int cards)
     {
        this.cards = cards;
     }

     public int getCards()
     {
        return cards;
     }

     public void setYellowCards(int yellowCards)
     {
        this.yellowCards = yellowCards;
     }

     public int getYellowCards()
     {
        return yellowCards;
     }

     public void setRedCards(int redCards)
     {
        this.redCards = redCards;
     }

     public int getRedCards()
     {
        return redCards;
     }

     public void setPasses(int passes)
     {
        this.passes = passes;
     }

     public int getPasses()
     {
        return passes;
     }

     public void setGoodPasses(int goodPases)
     {
        this.goodPases = goodPases;
     }

     public int getGoodPasses()
     {
        return goodPases;
     }

     public void setShortPasses(int shortPasses)
     {
        this.shortPasses = shortPasses;
     }

     public int getShortPasses()
     {
        return shortPasses;
     }

     public void setLongPasses(int longPasses)
     {
        this.longPasses = longPasses;
     }

     public int getLongPasses()
     {
        return longPasses;
     }

     public void setAssists(int assists)
     {
        this.assists = assists;
     }

     public int getAssists()
     {
        return assists;
     }

     public void setDribles(int dribles)
     {
        this.dribles = dribles;
     }

     public int getDribles()
     {
        return dribles;
     }

     public void setCenters(int centers)
     {
        this.centers = centers;
     }

     public int getCenters()
     {
        return centers;
     }

     public void setBallLosses(int ballLosses)
     {
        this.ballLosses = ballLosses;
     }

     public int getBallLosses()
     {
        return ballLosses;
     }
}
