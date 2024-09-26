package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.tfg.futstats.controllers.DTOs.LeagueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;

    // Realtions with other models in DB
    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Match> matches;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToMany
    private List<User> users;

    // League attributes
    private String name;
    private String president;
    private String nationality;

    // Constructors
    public League() {

    }

    public League(String name, String president, String nationality) {
        this.teams = new ArrayList<Team>();
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.users = new ArrayList<User>();
        this.name = name;
        this.president = president;
        this.nationality = nationality;
    }

    public League(LeagueDTO league) {
        this.teams = new ArrayList<Team>();
        this.matches = new ArrayList<Match>();
        this.players = new ArrayList<Player>();
        this.name = league.getName();
        this.president = league.getPresident();
        this.nationality = league.getNationality();
    }

    // Getters & Setters
    // --------------------------------------- TEAM ----------------------------
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

    public void setPlayer(Player player) {
        this.players.add(player);
        player.setLeague(this);
    }

    public void deletePlayer(Player player) {
        this.players.remove(player);
    }

    // --------------------------------------- USER
    // --------------------------------

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
}
