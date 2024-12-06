package com.tfg.futstats.errors;

import java.util.Date;


public class ErrorObject {

    // Http error
    private Integer errorCode;
    // Error description
    private String errorDesc;
    // Date of the error
    private Date date;

    // ----------------------------------- CONSTRUCTOR -----------------------------------\\

    public ErrorObject(Integer errorCode, String errorDesc, Date date)
    {
        super();
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.date = date;
    }

    public ErrorObject()
    {}

    // ----------------------------------- GETTERS AND SETTERS OF ERROR CODE -----------------------------------\\

    public void setErrorCode(Integer errorCode)
    {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode()
    {
        return this.errorCode;
    }

    // ----------------------------------- GETTERS AND SETTERS OF ERROR DESC -----------------------------------\\

    public void setErrorDesc(String errorDesc)
    {
        this.errorDesc = errorDesc;

    }

    public String getErrorDesc()
    {
        return this.errorDesc;
    }

    // ----------------------------------- GETTERS AND SETTERS OF THE DATE -----------------------------------\\

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    


    
    

}