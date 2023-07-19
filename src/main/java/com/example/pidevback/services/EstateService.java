package com.example.pidevback.services;

import java.util.List;
import java.util.Optional;

import com.example.pidevback.dto.EstateSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.pidevback.entities.Estate;
import com.example.pidevback.entities.Estate.EstateBuilder;
import com.example.pidevback.repositories.EstateRepository;

@Service
public class EstateService {

    @Autowired
    private EstateRepository estateRepository;

    //middlewares-alike
    public boolean isEstateOwner(Long userId, Long estateId) throws Exception {
        Estate found = estateRepository.findByOwnerIdAndId(userId, estateId);
        if (found == null) throw new Exception("Cannot find such estate that current user is owner of");
        return true;
    }

    public Estate saveEstate(Estate estate) throws Exception {
        return estateRepository.save(estate);
    }

    public Estate updateEstate(Estate estate) throws Exception {
        Estate es = estateRepository.findById(estate.getId()).orElse(null);
        if (es == null) throw new Exception("Provided estate ID doesn't exist");
        if (estate.getArea() != null) es.setArea(estate.getArea());
        if (estate.getName() != null) es.setName(estate.getName());
        if (estate.getPrice() != null) es.setPrice(estate.getPrice());
        es.setDescription(estate.getDescription());
        es.setDetails(estate.getDetails());
        es.setLocation(estate.getLocation());
        return estateRepository.save(es);
    }

    /*
    public List<Estate> getEstates(Estate es, int page) {
        EstateBuilder estate = Estate.builder();
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
        Page<Estate> result = es != null ? estateRepository.findAll(
                Example.of(estate.build()),
                PageRequest.of(page, 20, Sort.by("createdAt")))
                : estateRepository.findAll(
                PageRequest.of(page, 20, Sort.by("createdAt"))
        );

        return result.getContent();
    }
    */

    public List<Object[]> getHeatmap() {
        return estateRepository.findHeatmapData();
    }

    public Estate getEstate(Long id) {
        return estateRepository.findById(id).orElse(null);
    }

    public List<Estate> getUserEstates(Long id, int page) {
        return estateRepository.findByOwnerId(id, PageRequest.of(page, 20, Sort.by("createdAt")));
    }

    public List<Estate> getEstatesOnMap(Long minLat,Long maxLat, Long minLong,Long maxLong) {
        return estateRepository
                .findByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
                        minLat,
                        maxLat,
                        minLong,
                        maxLong,
                        PageRequest.of(0,20,Sort.by("price").ascending())
                );
    }

    public List<Estate> searchEstates(EstateSearchDto cr, int page){
        return estateRepository.searchEstates(
                cr.getMaxArea(),
                cr.getMinArea(),
                cr.getMaxPrice(),
                cr.getMinPrice(),
                cr.getMaxNbRoom(),
                cr.getMinNbRoom(),
                cr.getMaxFloor(),
                cr.getMinFloor(),
                PageRequest.of(page, 20, Sort.by("createdAt"))
        );
    }

}
