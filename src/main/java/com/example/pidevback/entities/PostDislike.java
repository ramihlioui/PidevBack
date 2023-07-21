package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonIgnore
    @ManyToOne
    Post post; // The post to like
}
