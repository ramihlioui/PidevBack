package com.exemple.pidevback.controller;

import com.example.pidevback.controllers.AppointmentController;
import com.example.pidevback.entities.Appointment;
import com.example.pidevback.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAppointments() {
        // Créer une liste de rendez-vous fictive pour le test
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(1L, LocalDate.now(), LocalDate.now(), null, null));
        appointments.add(new Appointment(2L, LocalDate.now(), LocalDate.now(), null, null));

        // Définir le comportement attendu du service
        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        // Appeler la méthode du contrôleur à tester
        ResponseEntity<List<Appointment>> responseEntity = appointmentController.getAllAppointments();

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Vérifier que la liste de rendez-vous retournée est la même que celle du service
        assertEquals(appointments, responseEntity.getBody());
    }

    @Test
    public void testGetAppointmentById() {
        // Créer un rendez-vous fictif pour le test
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(appointmentId, LocalDate.now(), LocalDate.now(), null, null);

        // Définir le comportement attendu du service
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(Optional.of(appointment));

        // Appeler la méthode du contrôleur à tester
        ResponseEntity<Appointment> responseEntity = appointmentController.getAppointmentById(appointmentId);

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Vérifier que le rendez-vous retourné est le même que celui du service
        assertEquals(appointment, responseEntity.getBody());
    }

    @Test
    public void testGetAppointmentById_NotFound() {
        // Créer un rendez-vous fictif pour le test
        Long appointmentId = 1L;

        // Définir le comportement attendu du service (retourner un Optional vide)
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(Optional.empty());

        // Appeler la méthode du contrôleur à tester
        ResponseEntity<Appointment> responseEntity = appointmentController.getAppointmentById(appointmentId);

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveAppointment() {
        // Créer un rendez-vous fictif pour le test
        Appointment appointmentToSave = new Appointment(1L, LocalDate.now(), LocalDate.now(), null, null);

        // Définir le comportement attendu du service
        when(appointmentService.saveAppointment(appointmentToSave)).thenReturn(appointmentToSave);

        // Appeler la méthode du contrôleur à tester
        ResponseEntity<Appointment> responseEntity = appointmentController.saveAppointment(appointmentToSave);

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Vérifier que le rendez-vous retourné est le même que celui du service
        assertEquals(appointmentToSave, responseEntity.getBody());
    }

    @Test
    public void testDeleteAppointmentById() {
        // Créer un ID de rendez-vous fictif pour le test
        Long appointmentId = 1L;

        // Appeler la méthode du contrôleur à tester
        ResponseEntity<Void> responseEntity = appointmentController.deleteAppointmentById(appointmentId);

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Vérifier que la méthode de suppression du service a été appelée une fois avec l'ID du rendez-vous
        verify(appointmentService, times(1)).deleteAppointmentById(appointmentId);
    }
}
