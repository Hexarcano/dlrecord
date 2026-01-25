package com.hexarcano.dlrecord.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexarcano.dlrecord.auth.application.implementation.LogIn;
import com.hexarcano.dlrecord.auth.application.implementation.SignUp;
import com.hexarcano.dlrecord.auth.application.port.out.AuthRepositoryPort;
import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.infrastructure.repository.AuthRepositoryAdapter;
import com.hexarcano.dlrecord.auth.infrastructure.service.DomainUserDetailsService;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepository;

@Configuration
public class AuthModuleConfig {
    @Bean
    AuthService authService(AuthRepositoryPort authRepository, PasswordEncoder passwordEncoder) {
        return new AuthService(new LogIn(authRepository), new SignUp(authRepository, passwordEncoder));
    }

    @Bean
    AuthRepositoryPort authRepositoryAdapter(
            JpaMaintainerRepository jpaMaintainerRepository,
            AuthenticationManager authenticationManager) {
        return new AuthRepositoryAdapter(jpaMaintainerRepository, authenticationManager);
    }

    @Bean
    UserDetailsService userDetailsService(JpaMaintainerRepository jpaMaintainerRepository) {
        return new DomainUserDetailsService(jpaMaintainerRepository);
    }
}
