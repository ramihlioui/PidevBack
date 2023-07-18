package com.example.pidevback.services;


import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.Users;
import com.example.pidevback.entities.enums.State;
import com.example.pidevback.repositories.ReclamationRepository;
import com.example.pidevback.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReclamationService {
    @Autowired
    private final ReclamationRepository reclamationRepository;
    @Autowired
    UserRepository userrepository;

    public ReclamationService(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    public Reclamation getReclamation(Long id) {
        return reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Reclamation> getReclamations() {
        return reclamationRepository.findAll();
    }

    public List<Reclamation> getOpenReclamations() {
        return reclamationRepository.findAll().stream().filter(reclamation -> !reclamation.getState().equals(State.CLOSED)).collect(Collectors.toList());
    }

    public List<Reclamation> getClosedReclamations() {
        return reclamationRepository.findAll().stream().filter(reclamation -> reclamation.getState().equals(State.CLOSED)).collect(Collectors.toList());
    }

    public Map<String, Long> getReclamationStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("Processing", reclamationRepository.findAll().stream().filter(rec -> rec.getState().equals(State.PROCESSING)).count());
        stats.put("Created", reclamationRepository.findAll().stream().filter(rec -> rec.getState().equals(State.CREATED)).count());
        stats.put("CreatedToday", reclamationRepository.findAll().stream().filter(rec -> rec.getCreationDate().equals(new Date())).count());
        stats.put("Total", (long) reclamationRepository.findAll().size());
        return stats;
    }

    public Reclamation saveReclamation(Reclamation reclamation, Long userId) {
        Users user = userrepository.findById(userId).orElse(null);
        if (user == null)
            throw new RuntimeException("No User");
        user.getReclamations().add(reclamation);
        reclamation.setClaimer(user);
        return reclamationRepository.save(reclamation);

    }

    public Reclamation processReclamation(Long id) {
        Reclamation rec = reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
        rec.setState(State.PROCESSING);
        return reclamationRepository.save(rec);
    }

    public Reclamation closeReclamation(Long id, String solution) {
        Reclamation rec = reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
        rec.setState(State.CLOSED);
        rec.setSolution(solution);
        return reclamationRepository.save(rec);
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

}
