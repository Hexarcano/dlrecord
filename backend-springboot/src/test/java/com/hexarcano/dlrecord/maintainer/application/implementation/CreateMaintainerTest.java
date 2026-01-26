package com.hexarcano.dlrecord.maintainer.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

@ExtendWith(MockitoExtension.class)
class CreateMaintainerTest {

    @Mock
    private MaintainerRepositoryPort maintainerRepository;

    @InjectMocks
    private CreateMaintainer createMaintainer;

    @Test
    void shouldCreateMaintainerSuccessfully() {
        // Arrange
        CreateMaintainerCommand command = new CreateMaintainerCommand("jdoe", "jdoe@example.com", "password123", false);
        Maintainer savedMaintainer = new Maintainer("uuid-123", "jdoe", "jdoe@example.com", "password123", false);

        when(maintainerRepository.save(any(Maintainer.class))).thenReturn(savedMaintainer);

        // Act
        Maintainer result = createMaintainer.createMaintainer(command);

        // Assert
        assertNotNull(result);
        assertEquals("uuid-123", result.getUuid());
        assertEquals("jdoe", result.getUsername());
        verify(maintainerRepository).save(any(Maintainer.class));
    }

    @Test
    void shouldThrowException_WhenUsernameIsInvalid() {
        // Arrange
        CreateMaintainerCommand command = new CreateMaintainerCommand("", "jdoe@example.com", "password123", false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createMaintainer.createMaintainer(command);
        });

        assertEquals("Username cannot be null or empty.", exception.getMessage());
        verify(maintainerRepository, never()).save(any(Maintainer.class));
    }

    @Test
    void shouldThrowException_WhenEmailIsInvalid() {
        // Arrange
        CreateMaintainerCommand command = new CreateMaintainerCommand("jdoe", "invalid-email", "password123", false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createMaintainer.createMaintainer(command);
        });

        assertEquals("Email is not valid.", exception.getMessage());
        verify(maintainerRepository, never()).save(any(Maintainer.class));
    }
}
