package com.example.pidevback.dto;


import com.example.pidevback.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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
