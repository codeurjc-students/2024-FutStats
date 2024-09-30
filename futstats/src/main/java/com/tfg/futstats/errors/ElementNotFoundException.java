package com.tfg.futstats.errors;

public class ElementNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ElementNotFoundException(String message) {
        super(message);
    }

}
