package com.example.pidevback.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.pidevback.entities.Estate_Details;
import com.example.pidevback.entities.Location;
import com.example.pidevback.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstateDto {

    @NotNull
    @Min(3)
    private String name;

    @NotNull
    private Long area;

    @NotNull
    private Long price;

    private String description;

    @NotNull
    private Estate_Details details;

    @NotNull
    private Location location;

    @NotNull
    private Long ownerId;

    private Users owner;
}
