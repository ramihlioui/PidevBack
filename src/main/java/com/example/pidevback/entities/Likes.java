package com.example.pidevback.entities;

import com.example.pidevback.enums.Reaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long LikeID;
    private Reaction reaction;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "PostId")
    private Posts post;
}
