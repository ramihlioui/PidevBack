package com.example.pidevback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatesOnMapSearch {
    @NotNull
    private Long minLat;
    @NotNull
    private Long maxLat;
    @NotNull
    private Long minLong;
    @NotNull
    private Long maxLong;
}
