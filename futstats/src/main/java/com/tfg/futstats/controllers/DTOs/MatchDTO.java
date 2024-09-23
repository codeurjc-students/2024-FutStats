package com.tfg.futstats.controllers.DTOs;

import java.sql.Date;

public class MatchDTO 
{
    private Date date;
    private String place;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }
}
