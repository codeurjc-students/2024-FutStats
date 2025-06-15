package com.tfg.futstats.controllers.dtos.team;

public class TeamUpdateDTO {

    // region attributes
    private long id;
    private String name;
    public String league;
    private int trophies;
    private String nationality;
    private String trainer;
    private String secondTrainer;
    private String president;
    private String stadium;
    private boolean image;
    // endregion

    // region Constructors
    public TeamUpdateDTO() {
    }
    // endregion

    // region Getters & setters
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

    public void setLeague(String league) {
        this.league = league;
    }

    public String getLeague() {
        return this.league;
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

    public boolean getImage() {
        return this.image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
    // endregion
}
