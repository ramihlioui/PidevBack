package com.example.pidevback.services;


import com.example.pidevback.repositories.ReclamationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;
}
