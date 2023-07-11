package com.example.pidevback.entities;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class CommentLike implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentLikeId;
    Date likedAt;
    @JsonIgnore
    @ManyToOne
    Users user;

    @JsonIgnore
    @ManyToOne
    PostComment postComment;


}
