package com.example.pidevback.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeatmapDto {
    private int count;
    private long lat;
    private long lng;
    private int cluster;
}
