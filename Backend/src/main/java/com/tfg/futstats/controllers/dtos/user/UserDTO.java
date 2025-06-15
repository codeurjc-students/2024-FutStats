package com.tfg.futstats.controllers.dtos.user;

import java.util.List;

public class UserDTO {

    // region attributes
    private long id;
    private String name;
    private String password;
    private String email;
    private List<String> roles;
    private boolean image;
    // endregion

    // region Constructors
    public UserDTO() {
    }
    // endregion

    // region Getters & setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean getImage() {
        return this.image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
    // endregion
}
