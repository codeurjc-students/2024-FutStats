package com.tfg.futstats.errors;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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

    @ExceptionHandler(value = ForbiddenAccessException.class)
    public ResponseEntity handleForbiddenAccessException(ForbiddenAccessException ex, WebRequest request) {
        ErrorObject error = new ErrorObject(HttpStatus.FORBIDDEN.value(), ex.getMessage(), new Date());
        return ResponseEntity.badRequest().body(error);
    }
}
