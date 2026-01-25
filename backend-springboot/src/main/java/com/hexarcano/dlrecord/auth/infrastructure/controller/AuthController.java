package com.hexarcano.dlrecord.auth.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.AuthResponse;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.LoginRequest;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.RefreshTokenRequest;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.SignUpRequest;
import com.hexarcano.dlrecord.config.token.JwtService;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Maintainer user = authService.logIn(request.toCommand());
        String accessToken = jwtService.generateToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<Maintainer> signUp(@Valid @RequestBody SignUpRequest request) {
        Maintainer createdUser = authService.signUp(request.toCommand());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        String username = jwtService.extractUsername(refreshToken);

        // In a real scenario, you should also check if the user exists in DB
        if (username != null && !jwtService.isTokenExpired(refreshToken)) {
            String accessToken = jwtService.generateToken(username);
            // Optionally rotate refresh token here too
            return ResponseEntity.ok(new AuthResponse(
                    accessToken, refreshToken));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
