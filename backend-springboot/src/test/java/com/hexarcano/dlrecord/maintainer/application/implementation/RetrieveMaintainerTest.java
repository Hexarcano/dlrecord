package com.hexarcano.dlrecord.maintainer.application.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexarcano.dlrecord.maintainer.application.port.out.MaintainerRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

@ExtendWith(MockitoExtension.class)
class RetrieveMaintainerTest {

    @Mock
    private MaintainerRepositoryPort maintainerRepository;

    @InjectMocks
    private RetrieveMaintainer retrieveMaintainer;

    @Test
    void findById_ShouldReturnMaintainer_WhenExists() {
        // Arrange
        String uuid = "uuid-123";
        Maintainer maintainer = new Maintainer(uuid, "jdoe", "jdoe@example.com", "password", false);

        when(maintainerRepository.findById(uuid)).thenReturn(Optional.of(maintainer));

        // Act
        Optional<Maintainer> result = retrieveMaintainer.findById(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("jdoe", result.get().getUsername());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        String uuid = "uuid-123";

        when(maintainerRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<Maintainer> result = retrieveMaintainer.findById(uuid);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ShouldReturnListOfMaintainers() {
        // Arrange
        Maintainer m1 = new Maintainer("1", "jdoe", "jdoe@example.com", "pass", false);
        Maintainer m2 = new Maintainer("2", "jane", "jane@example.com", "pass", true);

        when(maintainerRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        // Act
        List<Maintainer> result = retrieveMaintainer.findAll();

        // Assert
        assertEquals(2, result.size());
    }
}
