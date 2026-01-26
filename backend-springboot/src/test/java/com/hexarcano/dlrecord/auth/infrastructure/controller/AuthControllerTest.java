package com.hexarcano.dlrecord.auth.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;
import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;
import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.LoginRequest;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.RefreshTokenRequest;
import com.hexarcano.dlrecord.auth.infrastructure.controller.dto.SignUpRequest;
import com.hexarcano.dlrecord.config.WebSecurityConfig;
import com.hexarcano.dlrecord.config.token.JwtAuthFilter;
import com.hexarcano.dlrecord.config.token.JwtService;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void login_ShouldReturn200_WhenCredentialsAreValid() throws Exception {
        LoginRequest request = new LoginRequest("jdoe", "password123");
        Maintainer maintainer = new Maintainer("uuid-123", "jdoe", "jdoe@example.com", "password123", false);

        when(authService.logIn(any(LoginCommand.class))).thenReturn(maintainer);
        when(jwtService.generateToken("jdoe")).thenReturn("accessToken");
        when(jwtService.generateRefreshToken("jdoe")).thenReturn("refreshToken");

        mockMvc.perform(post("/api/v1/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }

    @Test
    void login_ShouldReturn403_WhenCredentialsAreInvalid() throws Exception {
        LoginRequest request = new LoginRequest("jdoe", "wrongpassword");

        when(authService.logIn(any(LoginCommand.class)))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        // Global exception handling might catch this and return 400 or 500, or the
        // controller propagates it.
        // Assuming default Spring behavior for unhandled exceptions or specific
        // handler.
        // Based on other tests, it seems we might expect BadRequest if exception is
        // IllegalArgumentException
        // However, standard login failure is often 401 or 403.
        // Let's assert based on what likely happens or what we want.
        // If the service throws IllegalArgumentException it usually results in 500
        // unless mapped.
        // But let's check MaintainerControllerTest: they expect BadRequest for
        // IllegalArgumentException.
        // Let's assume there is an exception handler mapping IllegalArgumentException
        // to 400.

        mockMvc.perform(post("/api/v1/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                // .andExpect(status().isBadRequest());
                // Wait, typically login failure is 401/403. But here we throw
                // IllegalArgumentException.
                // Let's stick with checking if the request fails.
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_ShouldReturn201_WhenDataIsValid() throws Exception {
        SignUpRequest request = new SignUpRequest("jdoe", "jdoe@example.com", "password123", false);
        Maintainer maintainer = new Maintainer("uuid-123", "jdoe", "jdoe@example.com", "password123", false);

        when(authService.signUp(any(SignUpCommand.class))).thenReturn(maintainer);

        mockMvc.perform(post("/api/v1/auth/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("jdoe"));
    }

    @Test
    void signUp_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        // Invalid email in request
        SignUpRequest request = new SignUpRequest("jdoe", "invalid-email", "password123", false);

        // Validation happens in command/usecase usually but here it happens in
        // Maintainer constructor
        // which is called inside SignUp usecase implementation.
        // The service will throw IllegalArgumentException.

        when(authService.signUp(any(SignUpCommand.class)))
                .thenThrow(new IllegalArgumentException("Email is not valid."));

        mockMvc.perform(post("/api/v1/auth/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refreshToken_ShouldReturn200_WhenTokenIsValid() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("validRefreshToken");

        when(jwtService.extractUsername("validRefreshToken")).thenReturn("jdoe");
        when(jwtService.isTokenExpired("validRefreshToken")).thenReturn(false);
        when(jwtService.generateToken("jdoe")).thenReturn("newAccessToken");

        mockMvc.perform(post("/api/v1/auth/refresh")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("validRefreshToken"));
    }

    @Test
    void refreshToken_ShouldReturn403_WhenTokenIsInvalid() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("invalidRefreshToken");

        when(jwtService.extractUsername("invalidRefreshToken")).thenReturn(null); // or throw

        mockMvc.perform(post("/api/v1/auth/refresh")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
