package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    @JsonIgnore
    @ManyToOne
    private Estate estate;
    @JsonIgnore
    @ManyToOne
    private Users users;

}