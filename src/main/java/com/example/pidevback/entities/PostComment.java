package com.example.pidevback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class PostComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postCommentId;
    String commentBody;

    Date commentedAt;


    @ManyToOne
    Users user; // The user who wants to comment

    @JsonIgnore
    @ManyToOne
    Post post; // The post to comment
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postReply")
    Set<PostComment> postComments;   //Reflexive association : A comment can have multiple replies


    @JsonIgnore
    @ManyToOne
    PostComment postReply;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postComment")
    Set<CommentLike> commentLikes;
}
