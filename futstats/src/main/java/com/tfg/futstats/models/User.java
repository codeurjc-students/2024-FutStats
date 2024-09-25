package com.tfg.futstats.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.controllers.DTOs.UserDTO;
import com.example.demo.models.leagueModels.League;
import com.example.demo.models.leagueModels.Player;
import com.example.demo.models.leagueModels.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User 
{

    //User attributes

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto generated ID
    private long id;    

    @Column(unique = true)
    private String name;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    protected List<League> belongedLeagues;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    protected List<Team> belongedTeams;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    protected List<Player> belongedPlayers;

    // If we will add more attributes in the future we have to add mappedBy in the
    // other class that relate to, to inform JPA which attribute "connects" the
    // entities

    //Constructors

    public User() 
    {

    }

    public User(String name, String password) 
    {
        this.name = name;
        this.password = password;
        this.belongedLeagues = new ArrayList<>();
        this.belongedTeams = new ArrayList<>();
        this.belongedPlayers = new ArrayList<>();
    }

    public User(UserDTO user)
    {
        this.name = user.getName();
        this.password = user.getPassword();
    }

    //Getters & Setters

    public long getId() 
    {
        return this.id;
    }

    public void setId(long id) 
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public List<League> getLeagues() 
    {
        return this.belongedLeagues;
    }

    public void setLeague(League newLeague) 
    {
        this.belongedLeagues.add(newLeague);
    }

    public List<Team> getTeams() 
    {
        return this.belongedTeams;
    }

    public void setTeam(Team newTeam) 
    {
        this.belongedTeams.add(newTeam);
    }

    public List<Player> getPlayers() 
    {
        return this.belongedPlayers;
    }

    public void setPlayer(Player newPlayer) 
    {
        this.belongedPlayers.add(newPlayer);
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getRole() {
        return "user";
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
    }

    @JsonIgnore
    public String getRole() {
        return "user";
    }
}

