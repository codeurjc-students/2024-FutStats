package com.tfg.futstats.models;

// region imports
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.futstats.controllers.dtos.league.LeagueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
//endregion

//As we want that this class be kept in the database we have to put this notation
@Entity
public class League {

    // region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // League attributes
    @Column(unique = true)
    @NotNull
    private String name;
    private String president;
    private String nationality;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    private boolean image;

    // Realtions with other models in DB
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match> matches;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Player> players;

    @ManyToMany(mappedBy = "belongedLeagues")
    @JsonIgnore
    private List<User> users;
    // endregion

    // region Constructors
    public League() {
        this.teams = new ArrayList<Team>();
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.users = new ArrayList<User>();
    }

    public League(String name, String president, String nationality, Blob imageFile, boolean image) {
        this.name = name;
        this.president = president;
        this.nationality = nationality;
        this.imageFile = imageFile;
        this.image = image;
        this.teams = new ArrayList<Team>();
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.users = new ArrayList<User>();
    }

    public League(LeagueDTO league) {
        this.id = league.getId();
        this.name = league.getName();
        this.president = league.getPresident();
        this.nationality = league.getNationality();
        this.image = league.getImage();
    }
    // endregion

    // region Getters & Setters
    // --------------------------------------- TEAM
    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setTeam(Team team) {
        this.teams.add(team);
        team.setLeague(this);
    }

    public void deleteTeam(Team team) {
        this.teams.remove(team);
    }

    // --------------------------------------- MATCH
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
    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
        player.setLeague(this);
    }

    public void deletePlayer(Player player) {
        this.players.remove(player);
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
        user.setLeague(this);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
    // endregion
}
