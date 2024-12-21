package com.tfg.futstats.controllers.dtos.user;

import java.util.List;

import com.tfg.futstats.models.User;

public class UserResponseDTO {
    private long id;
    private String name;
    private List<String> roles;
    private boolean image;
    
    public UserResponseDTO(){}

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.roles = user.getRoles();
        this.image = user.getImage();
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }

	public boolean getImage() {
		return this.image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}
}
