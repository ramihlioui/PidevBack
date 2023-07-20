package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    String postTitle;

    Date createdAt;

    String body;

    int nb_Signal;
    int nb_etoil;
@JsonIgnore
    @ManyToOne
    Users users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    Set<PostLike> postLikes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    Set<PostDislike> postDislikes;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    Set<PostComment> postComments;


}
