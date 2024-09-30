package com.tfg.futstats.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.futstats.security.jwt.LoginRequest;
import com.tfg.futstats.security.jwt.AuthResponse;
import com.tfg.futstats.security.jwt.AuthResponse.Status;
import com.tfg.futstats.security.jwt.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserLoginService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return userService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(request, response)));
    }
}
