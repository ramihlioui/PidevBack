package com.example.pidevback.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estate_Details details;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users owner;

}
