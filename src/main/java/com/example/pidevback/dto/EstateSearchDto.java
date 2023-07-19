package com.example.pidevback.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class EstateSearchDto {

    @Nullable
    private Long minArea;
    @Nullable
    private Long maxArea;

    @Nullable
    private Long minPrice;
    @Nullable
    private Long maxPrice;

    @Nullable
    private Long minNbRoom;
    @Nullable
    private Long maxNbRoom;

    @Nullable
    private Long minFloor;
    @Nullable
    private Long maxFloor;
}
