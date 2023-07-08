package com.example.pidevback.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Appointment {
    @Id
    private Long id;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    @ManyToOne
    private Estate estate;
}
