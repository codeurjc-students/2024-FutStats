package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

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

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private Long id;

    // User attributes
    @Column(unique = true)
    private String name;

    private String password;

    private List<String> roles;

    // Realtions with other models in DB
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_leagues",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "league_id"))
    private List<League> belongedLeagues;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_teams",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> belongedTeams;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_players",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> belongedPlayers;

    @Lob
	@JsonIgnore
	private Blob imageFile;

	private boolean image;

    // Constructors

    public User() {}

    public User(String name, String password, Blob imageFile, boolean image, String... roles) {
        this.name = name;
        this.password = password;
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
        this.roles = List.of(roles);
        this.image = image;
        this.imageFile = imageFile;
    }

    public User(UserDTO user) {
        this.name = user.getName();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.image = user.getImage();
    }

    // Getters & Setters

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

    public void removeLeague(League league){
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

    public void removeTeam(Team team){
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

    public void removePlayer(Player player){
        this.belongedPlayers.remove(player);
    }

    // --------------------------------
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
}
