package com.example.pidevback.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Estate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long area;

    private Long price;

    private String description;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Estate_Details details;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users owner;

}
