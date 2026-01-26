package com.hexarcano.dlrecord.maintainer.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

@ExtendWith(MockitoExtension.class)
class UpdateMaintainerTest {

    @Mock
    private MaintainerRepositoryPort maintainerRepository;

    @InjectMocks
    private UpdateMaintainer updateMaintainer;

    @Test
    void shouldUpdateMaintainerSuccessfully_WhenExists() {
        // Arrange
        String uuid = "uuid-123";
        UpdateMaintainerCommand command = new UpdateMaintainerCommand("jdoe_updated", "jdoe@example.com", "newpass",
                false);
        Maintainer existing = new Maintainer(uuid, "jdoe", "jdoe@example.com", "pass", false);

        when(maintainerRepository.findById(uuid)).thenReturn(Optional.of(existing));
        when(maintainerRepository.save(any(Maintainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Maintainer> result = updateMaintainer.updateMaintainer(uuid, command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("jdoe_updated", result.get().getUsername());
        verify(maintainerRepository).save(any(Maintainer.class));
    }

    @Test
    void shouldReturnEmpty_WhenNotExists() {
        // Arrange
        String uuid = "uuid-123";
        UpdateMaintainerCommand command = new UpdateMaintainerCommand("jdoe", "jdoe@example.com", "pass", false);

        when(maintainerRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<Maintainer> result = updateMaintainer.updateMaintainer(uuid, command);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowException_WhenUsernameIsInvalid() {
        // Arrange
        String uuid = "uuid-123";
        UpdateMaintainerCommand command = new UpdateMaintainerCommand("", "jdoe@example.com", "pass", false);
        Maintainer existing = new Maintainer(uuid, "jdoe", "jdoe@example.com", "pass", false);

        when(maintainerRepository.findById(uuid)).thenReturn(Optional.of(existing));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateMaintainer.updateMaintainer(uuid, command);
        });

        assertEquals("Username cannot be null or empty.", exception.getMessage());
        verify(maintainerRepository, never()).save(any(Maintainer.class));
    }
}
