package com.example.pidevback.dto;


import com.example.pidevback.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Min(3)
    @Max(50)
    private String fullName;

    @NotNull
    @Email
    private String email;


    private Boolean enabled;

    @NotNull
    private Role role;
}
