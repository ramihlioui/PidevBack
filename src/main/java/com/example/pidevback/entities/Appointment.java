package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate dateDebut;
    @JsonIgnore
    @ManyToOne
    private Estate estate;
    @JsonIgnore
    @ManyToOne
    private Users users;

}