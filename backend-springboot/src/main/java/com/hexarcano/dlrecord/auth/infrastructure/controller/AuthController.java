package com.hexarcano.dlrecord.auth.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Maintainer> login(@RequestBody Credentials credentials) {
        Maintainer user = authService.logIn(credentials);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<Maintainer> signUp(@RequestBody Maintainer maintainer) {
        Maintainer createdUser = authService.signUp(maintainer);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
