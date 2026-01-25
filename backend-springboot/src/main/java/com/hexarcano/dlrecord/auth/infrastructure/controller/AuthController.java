package com.hexarcano.dlrecord.auth.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.LoginRequest;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.SignUpRequest;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Maintainer> login(@Valid @RequestBody LoginRequest request) {
        Maintainer user = authService.logIn(request.toCommand());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<Maintainer> signUp(@Valid @RequestBody SignUpRequest request) {
        Maintainer createdUser = authService.signUp(request.toCommand());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
