package com.example.pidevback.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class PostDislike implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postDislikeId;
    Date dislikedAt;
    @ManyToOne
    Users user; // The user who clicked Like

    @ManyToOne
    Post post; // The post to like
}
