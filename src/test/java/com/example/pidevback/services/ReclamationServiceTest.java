package com.example.pidevback.services;

import com.example.pidevback.TestUtils;
import com.example.pidevback.repositories.ReclamationRepository;
import com.example.pidevback.repositories.UserRepository;
import org.junit.jupiter.api.Test;

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
    void testCreateReclamationWhenValid() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.of(TestUtils.RECLAMATION));
        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(TestUtils.USER));
        reclamationService.saveReclamation(TestUtils.RECLAMATION,TestUtils.USER_ID);
        assertThat(reclamationService.getReclamation(TestUtils.RECLAMATION_ID)).usingRecursiveComparison()
                .isEqualTo(TestUtils.RECLAMATION);
    }

    @Test
    void testCreateReclamationWhenUserNull_thenException() {
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reclamationService.saveReclamation(TestUtils.RECLAMATION,TestUtils.USER_ID)).isExactlyInstanceOf(
                RuntimeException.class);
    }

    @Test
    void Test_getForExistingReclamation_returnsReclamation() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.of(TestUtils.RECLAMATION));

        assertThat(reclamationService.getReclamation(TestUtils.RECLAMATION_ID)).usingRecursiveComparison()
                .isEqualTo(TestUtils.RECLAMATION);
    }

    @Test
    void Test_getForNonExistingReclamation_throwsException() {
        when(reclamationRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reclamationService.getReclamation(TestUtils.RECLAMATION_ID)).isExactlyInstanceOf(
                RuntimeException.class);
    }

}
