package com.example.pidevback.services;


import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.enums.State;
import com.example.pidevback.repositories.ReclamationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    public Reclamation getReclamation(Long id) {
        return reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Reclamation saveReclamation (Reclamation reclamation){
        return reclamationRepository.save(reclamation);
    }

    public Reclamation ProcessReclamation (Reclamation reclamation){
        Reclamation rec = reclamationRepository.findById(reclamation.getId()).orElseThrow(RuntimeException::new);
        rec.setState(State.PROCESSING);
        return reclamationRepository.save(rec);
    }

    public Reclamation closeReclamation (Reclamation reclamation,String solution){
        Reclamation rec = reclamationRepository.findById(reclamation.getId()).orElseThrow(RuntimeException::new);
        rec.setState(State.CLOSED);
        rec.setSolution(solution);
        return reclamationRepository.save(rec);
    }


}
