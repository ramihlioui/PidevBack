package com.example.pidevback.services;

import com.example.pidevback.entities.Appointment;
import com.example.pidevback.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public  List<Appointment> retrieveAll() {
        return appointmentRepository.findAll();
    }

    public Appointment retreiveById(Long id)
    {
       return appointmentRepository.getReferenceById(id);
    }

}
