package com.example.pidevback.dto;


import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String password;

   @NotNull
    private List<RoleDto> roles;
}
