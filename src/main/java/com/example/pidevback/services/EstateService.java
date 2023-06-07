package com.example.pidevback.services;

import com.example.pidevback.Mapper.EstateMapper;
import com.example.pidevback.dto.EstateDto;
import com.example.pidevback.entities.Estate;
import com.example.pidevback.entities.Estate_Details;
import com.example.pidevback.entities.Estate.EstateBuilder;
import com.example.pidevback.repositories.EstateRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstateService {

    @Autowired
    private EstateRepository estateRepository;

    public Estate saveEstate(Estate estate) throws Exception {
        return estateRepository.save(estate);
    }
    
    public Estate updateEstate(Estate estate) throws Exception {
        Estate es = estateRepository.findById(estate.getId()).orElse(null);
        if(es == null) throw new Exception("Provided estate ID doesn't exist");
        if(estate.getArea() != null) es.setArea(estate.getArea());
        if(estate.getName() != null) es.setName(estate.getName());
        if(estate.getPrice() != null) es.setPrice(estate.getPrice());
        es.setDescription(estate.getDescription());
        es.setDetails(estate.getDetails());
        es.setLocation(estate.getLocation());
        return estateRepository.save(es);
    }

    public List<Estate> getEstates(Estate es) {
        EstateBuilder estate = Estate.builder();
        log.info(es.getDetails().getFloors().toString());
        if (es != null) {
            if (es.getDetails() != null)
                estate.details(es.getDetails());
            if (es.getLocation() != null)
                estate.location(es.getLocation());
            if (es.getOwner() != null)
                estate.owner(es.getOwner());
            if (es.getName() != null)
                estate.name(es.getName());
            if (es.getArea() != null)
                estate.area(es.getArea());
            if (es.getPrice() != null)
                estate.price(es.getPrice());
        }

        return es != null ? estateRepository.findAll( Example.of(estate.build())) : estateRepository.findAll();
    }

}
