package com.example.pidevback.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long CommentID;

}
