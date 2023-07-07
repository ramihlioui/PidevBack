package com.example.pidevback.controllers;


import com.example.pidevback.entities.Appointment;
import com.example.pidevback.services.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@Slf4j
public class AppointmentController {


    @Autowired
    private AppointmentService appointmentService;


    @PostMapping("/")
    public ResponseEntity<Appointment> post(@RequestBody Appointment appointment) {
        log.info("log Start, AppointmentService_POST "+appointment);
        Appointment app = appointmentService.save(appointment);
        log.info("log Stop, AppointmentService_POST "+ app);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @GetMapping("")
    public ResponseEntity< List <Appointment>> retreiveAll() {
        log.info("log Start, AppointmentService_GET ");
       List <Appointment> appointment = appointmentService.retrieveAll();
        log.info("log Stop, AppointmentService_GET ");
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }


}
