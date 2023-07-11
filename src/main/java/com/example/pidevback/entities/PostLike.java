package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class PostLike implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postLikeId;

    Date likedAt;

    Boolean isLiked ;


    @ManyToOne
    Users user; // The user who clicked Like

    @JsonIgnore
    @ManyToOne
    Post post; // The post to like
}
