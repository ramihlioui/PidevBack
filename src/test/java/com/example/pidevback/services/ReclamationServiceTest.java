package com.example.pidevback.services;

import com.example.pidevback.TestUtils;
import com.example.pidevback.dto.Solution;
import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.repositories.ReclamationRepository;
import com.example.pidevback.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReclamationServiceTest {
    private final ReclamationRepository reclamationRepositoryMock = mock(ReclamationRepository.class);
    private final UserRepository userRepositoryMock = mock(UserRepository.class);
    private final ReclamationService reclamationService = new ReclamationService(reclamationRepositoryMock, userRepositoryMock);

    @Test
    void Test_getForExistingReclamation_returnsReclamation() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.of(TestUtils.RECLAMATION_OPEN));

        assertThat(reclamationService.getReclamation(TestUtils.RECLAMATION_ID)).usingRecursiveComparison()
                .isEqualTo(TestUtils.RECLAMATION_OPEN);
    }

    @Test
    void Test_getForNonExistingReclamation_throwsException() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reclamationService.getReclamation(TestUtils.RECLAMATION_ID)).isExactlyInstanceOf(
                RuntimeException.class);
    }

    @Test
    void Test_closeReclamation_whenValid() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.of(TestUtils.RECLAMATION_TO_CLOSE));
        Reclamation reclamation = reclamationService.closeReclamation(TestUtils.RECLAMATION_ID_TO_CLOSE,new Solution("solution"));

        assertThat(reclamation.getSolution()).isEqualTo("solution");
    }

    @Test
    void Test_closeReclamation_whenNotExisting_throwsException() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reclamationService.getReclamation(TestUtils.RECLAMATION_ID_TO_CLOSE)).isExactlyInstanceOf(
                RuntimeException.class);
    }

    @Test
    void Test_getReclamations_whenValid() {
        when(reclamationRepositoryMock.findAll()).thenReturn(TestUtils.ALL);

        assertThat(reclamationService.getReclamations().size()).isEqualTo(2);
    }

    @Test
    void Test_getOpenReclamations_whenValid() {
        when(reclamationRepositoryMock.findAll()).thenReturn(TestUtils.ALL);

        assertThat(reclamationService.getOpenReclamations().get(0)).isEqualTo(TestUtils.RECLAMATION_OPEN);
    }

    @Test
    void Test_getClosedReclamations_whenValid() {
        when(reclamationRepositoryMock.findAll()).thenReturn(TestUtils.ALL);

        assertThat(reclamationService.getClosedReclamations().get(0)).isEqualTo(TestUtils.RECLAMATION_CLOSED);
    }

    @Test
    void Test_getReclamationsStats_whenValid() {
        when(reclamationRepositoryMock.findAll()).thenReturn(TestUtils.ALL);
        Map<String,Long> stats = reclamationService.getReclamationStats();

        assertThat(stats.get("Closed")).isEqualTo(1);
        assertThat(stats.get("Open")).isEqualTo(1);
        assertThat(stats.get("Total")).isEqualTo(2);
        assertThat(stats.get("CreatedToday")).isEqualTo(2);
    }
}
