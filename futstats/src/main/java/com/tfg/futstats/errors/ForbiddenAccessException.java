package com.tfg.futstats.errors;

public class ForbiddenAccessException extends RuntimeException{
    private static final long serialVersionUID = 1;
    
    public ForbiddenAccessException(String errorMessage) {
        super(errorMessage);
    }

}
