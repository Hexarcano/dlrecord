package com.hexarcano.dlrecord.maintainer.application.implementation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteMaintainerTest {

    @Mock
    private MaintainerRepositoryPort maintainerRepository;

    @InjectMocks
    private DeleteMaintainer deleteMaintainer;

    @Test
    void shouldReturnTrue_WhenDeleteSuccessful() {
        // Arrange
        String uuid = "uuid-123";
        when(maintainerRepository.delete(uuid)).thenReturn(true);

        // Act
        boolean result = deleteMaintainer.deleteMaintainer(uuid);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_WhenMaintainerDoesNotExist() {
        // Arrange
        String uuid = "uuid-123";
        when(maintainerRepository.delete(uuid)).thenReturn(false);

        // Act
        boolean result = deleteMaintainer.deleteMaintainer(uuid);

        // Assert
        assertFalse(result);
    }
}
