package com.example.pidevback.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long PostId;
    private long description;
    private long image;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL)
    private List<Likes> likes;
}
