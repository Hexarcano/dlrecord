package com.hexarcano.dlrecord.auth.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;
import com.hexarcano.dlrecord.auth.application.port.out.AuthRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

@ExtendWith(MockitoExtension.class)
class LogInTest {

    @Mock
    private AuthRepositoryPort authRepository;

    @InjectMocks
    private LogIn logIn;

    @Test
    void shouldLogInSuccessfully() {
        // Arrange
        String username = "jdoe";
        String password = "password123";
        LoginCommand command = new LoginCommand(username, password);
        Maintainer expectedMaintainer = new Maintainer("uuid-123", username, "jdoe@example.com", password, false);

        when(authRepository.logIn(username, password)).thenReturn(expectedMaintainer);

        // Act
        Maintainer result = logIn.logIn(command);

        // Assert
        assertEquals(expectedMaintainer, result);
        verify(authRepository).logIn(username, password);
    }

    @Test
    void shouldThrowException_WhenCredentialsAreInvalid() {
        // Arrange
        String username = "jdoe";
        String password = "wrongpassword";
        LoginCommand command = new LoginCommand(username, password);

        when(authRepository.logIn(username, password)).thenThrow(new IllegalArgumentException("Invalid credentials"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            logIn.logIn(command);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authRepository).logIn(username, password);
    }
}
