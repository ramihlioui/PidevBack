package com.example.pidevback.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.example.pidevback.dto.EstatesOnMapSearch;
import com.example.pidevback.dto.HeatmapDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.pidevback.Mapper.EstateMapper;
import com.example.pidevback.dto.EstateDto;
import com.example.pidevback.entities.Estate;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.UserRepository;
import com.example.pidevback.services.EstateService;

@RestController
@Slf4j
@RequestMapping("/estates")
public class EstateController {

    @Autowired
    private EstateService estateService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Estate> getEstate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(estateService.getEstate(id));
    }

    @PostMapping("/")
    public ResponseEntity<Estate> createEstate(@RequestBody EstateDto estateDto, Authentication auth) {
        try {
            //
            Users owner = userRepository.findUserByEmail(auth.getName()).orElse(null);
            if (owner != null)
                estateDto.setOwner(owner);
            //
            if (owner == null || estateDto.getName() == null || estateDto.getArea() == null
                    || estateDto.getPrice() == null)
                throw new Exception("Owner, Name, Price and area are required.");

            //
            Estate estateFromDto = EstateMapper.Instance.estateDtoToEstate(estateDto);
            Estate estate = estateService.saveEstate(estateFromDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(estate);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estate> updateEstate(@RequestBody EstateDto estateDto, @PathVariable Long id, Authentication auth) {
        try {
            Users owner = userRepository.findUserByEmail(auth.getName()).orElse(null);
            log.info(auth.getName());
            log.info(owner.getId().toString());
            if (owner != null)
                estateDto.setOwner(owner);
            else throw new Exception("Cannot find a user with such email address");
            //if any required data is null
            if (estateDto.getName() == null || estateDto.getArea() == null
                    || estateDto.getPrice() == null)
                throw new Exception("Name, Price and area are required.");
            //if not owner of intended to be updated estate
            if(!estateService.isEstateOwner(owner.getId(),id))
                throw new Exception("You do not own this estate to update");
            //proceed
            Estate estateFromDto = EstateMapper.Instance.estateDtoToEstate(estateDto);
            estateFromDto.setId(id);
            Estate updated = estateService.updateEstate(estateFromDto);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<Estate>> getEstates(@Nullable @RequestBody Estate es, @RequestParam int page) {
        try {
            List<Estate> estates = estateService.getEstates(es, page);
            return ResponseEntity.status(HttpStatus.OK).body(estates);
        } catch (Exception e) {
            //return ResponseEntity.status(200).body(new ArrayList<Estate>());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/my-estates")
    public ResponseEntity<List<Estate>> getMyEstates(@Nullable @RequestParam int page, Authentication auth) {
        try {
            Users me = userRepository.findUserByEmail(auth.getName()).orElse(null);
            if(me == null) throw new Exception();
            List<Estate> estates = estateService.getUserEstates(me.getId(), page);
            return ResponseEntity.status(HttpStatus.OK).body(estates);
        } catch (Exception e) {
            //return ResponseEntity.status(200).body(new ArrayList<Estate>());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Estate>> getUserEstates(@PathVariable Long id, @RequestParam int page ){
        return ResponseEntity.status(HttpStatus.OK).body(estateService.getUserEstates(id,page));
    }

    @PostMapping("/map-search")
    public ResponseEntity<List<Estate>> getEstatesOnMap(@RequestBody EstatesOnMapSearch estatesOnMapSearch){
        return ResponseEntity.status(HttpStatus.OK).body(estateService.getEstatesOnMap(
                estatesOnMapSearch.getMinLat(),
                estatesOnMapSearch.getMaxLat(),
                estatesOnMapSearch.getMinLong(),
                estatesOnMapSearch.getMaxLong()
        ));
    }

    @GetMapping("/heatmap")
    public ResponseEntity<List<HeatmapDto>> getHeatmap() {
        try {
            List<Object[]> heatmap = estateService.getHeatmap();
            List<HeatmapDto> data = new ArrayList();
            for (Object[] obj : heatmap) {
                HeatmapDto h = new HeatmapDto();
                h.setCount(((BigInteger) obj[0]).intValue());
                h.setLng(((Double) obj[1]).intValue());
                h.setLat(((Double) obj[2]).intValue());
                h.setCluster((int) obj[3]);
                data.add(h);
            }
            return ResponseEntity.status(HttpStatus.OK).body(data);
        } catch (Exception e) {
            //return ResponseEntity.status(200).body(new ArrayList<Estate>());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

}
