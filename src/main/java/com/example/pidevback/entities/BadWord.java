package com.example.pidevback.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@Entity
public class BadWord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdWord;


    String word;
}
