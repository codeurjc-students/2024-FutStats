package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import com.tfg.futstats.controllers.dtos.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

    // User attributes

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    private List<String> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_leagues",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "league_id"))
    private List<League> belongedLeagues;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_teams",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> belongedTeams;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_players",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> belongedPlayers;

    // If we will add more attributes in the future we have to add mappedBy in the
    // other class that relate to, to inform JPA which attribute "connects" the
    // entities

    // Constructors

    public User() {}

    public User(String name, String password, String... roles) {
        this.name = name;
        this.password = password;
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
        this.roles = List.of(roles);
    }

    public User(UserDTO user) {
        this.name = user.getName();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    // Getters & Setters

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<League> getLeagues() {
        return this.belongedLeagues;
    }

    public void setLeague(League newLeague) {
        this.belongedLeagues.add(newLeague);
    }

    public void setLeagues(List<League> newLeagues) {
        this.belongedLeagues = newLeagues;
    }

    public void removeLeague(League league){
        this.belongedLeagues.remove(league);
    }

    public List<Team> getTeams() {
        return this.belongedTeams;
    }

    public void setTeam(Team newTeam) {
        this.belongedTeams.add(newTeam);
    }

    public void setTeams(List<Team> newTeams) {
        this.belongedTeams = newTeams;
    }

    public void removeTeam(Team team){
        this.belongedTeams.remove(team);
    }

    public List<Player> getPlayers() {
        return this.belongedPlayers;
    }

    public void setPlayer(Player newPlayer) {
        this.belongedPlayers.add(newPlayer);
    }

    public void setPlayers(List<Player> newPlayers) {
        this.belongedPlayers = newPlayers;
    }

    public void removePlayer(Player player){
        this.belongedPlayers.remove(player);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String role)
    {
        this.roles.add(role);
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
    }
}
