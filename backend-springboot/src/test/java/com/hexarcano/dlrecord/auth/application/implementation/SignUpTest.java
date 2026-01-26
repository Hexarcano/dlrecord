package com.hexarcano.dlrecord.auth.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;
import com.hexarcano.dlrecord.auth.application.port.out.AuthRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

@ExtendWith(MockitoExtension.class)
class SignUpTest {

    @Mock
    private AuthRepositoryPort authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignUp signUp;

    @Test
    void shouldSignUpSuccessfully() {
        // Arrange
        SignUpCommand command = new SignUpCommand("jdoe", "jdoe@example.com", "password123", false);
        String encodedPassword = "encodedPassword123";
        Maintainer savedMaintainer = new Maintainer("uuid-123", "jdoe", "jdoe@example.com", encodedPassword, false);

        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(authRepository.signUp(any(Maintainer.class))).thenReturn(savedMaintainer);

        // Act
        Maintainer result = signUp.signUp(command);

        // Assert
        assertEquals(savedMaintainer, result);
        verify(passwordEncoder).encode("password123");
        verify(authRepository).signUp(any(Maintainer.class));
    }

    @Test
    void shouldThrowException_WhenUserAlreadyExists() {
        // Arrange
        SignUpCommand command = new SignUpCommand("jdoe", "jdoe@example.com", "password123", false);
        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(authRepository.signUp(any(Maintainer.class)))
                .thenThrow(new IllegalArgumentException("User already exists"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            signUp.signUp(command);
        });

        assertEquals("User already exists", exception.getMessage());
        verify(authRepository).signUp(any(Maintainer.class));
    }

    @Test
    void shouldThrowException_WhenDataIsInvalid() {
        // Arrange - Invalid Email
        SignUpCommand command = new SignUpCommand("jdoe", "invalid-email", "password123", false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            signUp.signUp(command);
        });

        // The validation happens in the Maintainer constructor, so we check for its
        // message
        assertEquals("Email is not valid.", exception.getMessage());
        verify(authRepository, never()).signUp(any(Maintainer.class));
    }
}
