package com.example.pidevback.entities;


import com.example.pidevback.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fullName;

    private String email;

    private String password;

    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

}
