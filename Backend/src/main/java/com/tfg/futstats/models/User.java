package com.tfg.futstats.models;

// region imports
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import com.tfg.futstats.controllers.dtos.user.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
//endregion

@Entity
public class User {

    // region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // User attributes
    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private List<String> roles;

    // Realtions with other models in DB
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_leagues", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "league_id"))
    private List<League> belongedLeagues;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_teams", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> belongedTeams;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_players", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> belongedPlayers;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    private boolean image;
    // endregion

    // region Constructors

    public User() {
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
    }

    public User(String name, String password, String email, Blob imageFile, boolean image, String... roles) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
        this.roles = new ArrayList<>(Arrays.asList(roles));
        this.image = image;
        this.imageFile = imageFile;
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
    }

    public User(UserDTO user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.image = user.getImage();
    }
    // endregion

    // region Getters & Setters
    public long getId() {
        return this.id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ------------------------------------ LEAGUE
    // -------------------------------------
    public List<League> getLeagues() {
        return this.belongedLeagues;
    }

    public void setLeague(League newLeague) {
        this.belongedLeagues.add(newLeague);
    }

    public void setLeagues(List<League> newLeagues) {
        this.belongedLeagues = newLeagues;
    }

    public void removeLeague(League league) {
        this.belongedLeagues.remove(league);
    }

    // ------------------------------------ TEAM
    // ------------------------------------
    public List<Team> getTeams() {
        return this.belongedTeams;
    }

    public void setTeam(Team newTeam) {
        this.belongedTeams.add(newTeam);
    }

    public void setTeams(List<Team> newTeams) {
        this.belongedTeams = newTeams;
    }

    public void removeTeam(Team team) {
        this.belongedTeams.remove(team);
    }

    // --------------------------------------- PLAYER
    // --------------------------------
    public List<Player> getPlayers() {
        return this.belongedPlayers;
    }

    public void setPlayer(Player newPlayer) {
        this.belongedPlayers.add(newPlayer);
    }

    public void setPlayers(List<Player> newPlayers) {
        this.belongedPlayers = newPlayers;
    }

    public void removePlayer(Player player) {
        this.belongedPlayers.remove(player);
    }

    // --------------------------------
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String role) {
        this.roles.add(role);
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
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
