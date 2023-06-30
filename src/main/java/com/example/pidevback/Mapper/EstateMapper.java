package com.example.pidevback.Mapper;

import com.example.pidevback.dto.EstateDto;
import com.example.pidevback.entities.Estate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EstateMapper {

    EstateMapper Instance = Mappers.getMapper(EstateMapper.class);


    Estate estateDtoToEstate(EstateDto estateDto);

    EstateDto estateToEstateDto(Estate estate);


}
