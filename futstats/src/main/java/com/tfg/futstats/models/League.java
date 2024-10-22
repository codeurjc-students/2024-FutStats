package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import com.tfg.futstats.controllers.dtos.LeagueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

//As we want that this class be kept in the database we have to put this notation
@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // Realtions with other models in DB
    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Match> matches;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;

        // Relación de muchos a muchos con User
    @ManyToMany(mappedBy = "belongedLeagues")
    private List<User> user;

    // League attributes
    @Column(unique = true)
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
