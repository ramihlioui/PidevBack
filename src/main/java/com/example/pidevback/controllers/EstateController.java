package com.example.pidevback.controllers;

import com.example.pidevback.Mapper.EstateMapper;
import com.example.pidevback.dto.EstateDto;
import com.example.pidevback.entities.Estate;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.UserRepository;
import com.example.pidevback.services.EstateService;
import com.example.pidevback.services.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/estates")
@Slf4j
public class EstateController {

    @Autowired
    private EstateService estateService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<Estate> createEstate(@RequestBody EstateDto estateDto) {
        try {
            //
            Users owner = userRepository.findById(estateDto.getOwnerId()).orElse(null);
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

    @PutMapping("/")
    public ResponseEntity<Estate> updateEstate(@RequestBody EstateDto estateDto) {
        try {
            Users owner = userRepository.findById(estateDto.getOwnerId()).orElse(null);
            if (owner != null)
                estateDto.setOwner(owner);
            //
            if (owner == null || estateDto.getName() == null || estateDto.getArea() == null
                    || estateDto.getPrice() == null)
                throw new Exception("Owner, Name, Price and area are required.");
            Estate estateFromDto = EstateMapper.Instance.estateDtoToEstate(estateDto);
            Estate updated = estateService.saveEstate(estateFromDto);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<Estate>> getEstates(@Nullable @RequestBody Estate es) {
        try {
            List<Estate> estates = estateService.getEstates(es);
            return ResponseEntity.status(HttpStatus.OK).body(estates);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

}
