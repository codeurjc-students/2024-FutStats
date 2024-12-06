package com.tfg.futstats.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tfg.futstats.controllers.LeagueController;
import com.tfg.futstats.controllers.MatchController;
import com.tfg.futstats.controllers.Playercontroller;
import com.tfg.futstats.controllers.TeamController;
import com.tfg.futstats.controllers.UserController;

@RestControllerAdvice(assignableTypes = {LeagueController.class, MatchController.class, Playercontroller.class, TeamController.class, UserController.class})
public class GlobalExceptionHandler{

    @ExceptionHandler(value = ElementNotFoundException.class)
    public ResponseEntity handleElementNotFoundException() {
        return ResponseEntity.notFound().build();
    }

}
