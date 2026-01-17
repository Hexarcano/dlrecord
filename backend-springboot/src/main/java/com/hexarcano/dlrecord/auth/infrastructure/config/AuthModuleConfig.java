package com.hexarcano.dlrecord.auth.infrastructure.config;

import com.hexarcano.dlrecord.auth.application.implementation.LogIn;
import com.hexarcano.dlrecord.auth.application.implementation.SignUp;
import com.hexarcano.dlrecord.auth.application.port.out.IAuthRepository;
import com.hexarcano.dlrecord.auth.application.service.AuthService;
import com.hexarcano.dlrecord.auth.infrastructure.repository.AuthRepositoryAdapter;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthModuleConfig {
    @Bean
    AuthService authService(
            IAuthRepository authRepository,
            PasswordEncoder passwordEncoder) {
        return new AuthService(
                new LogIn(authRepository),
                new SignUp(authRepository, passwordEncoder));
    }

    @Bean
    IAuthRepository authRepositoryAdapter(
            JpaMaintainerRepository jpaMaintainerRepository,
            AuthenticationManager authenticationManager) {
        return new AuthRepositoryAdapter(jpaMaintainerRepository, authenticationManager);
    }

    @Bean
    UserDetailsService userDetailsService(
            JpaMaintainerRepository jpaMaintainerRepository) {
        return new com.hexarcano.dlrecord.auth.infrastructure.service.DomainUserDetailsService(jpaMaintainerRepository);
    }
}
