package com.example.pidevback.services;


import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.ReclamationRepository;
import com.example.pidevback.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userrepository;

    public ReclamationService(ReclamationRepository reclamationRepository, UserRepository userrepository) {
        this.reclamationRepository = reclamationRepository;
        this.userrepository = userrepository;
    }

    public Reclamation getReclamation(Long id) {
        return reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Reclamation> getReclamations() {
        return reclamationRepository.findAll();
    }

    public List<Reclamation> getOpenReclamations() {
        return reclamationRepository.findAll().stream().filter(reclamation -> !reclamation.isTreated()).collect(Collectors.toList());
    }

    public List<Reclamation> getClosedReclamations() {
        return reclamationRepository.findAll().stream().filter(Reclamation::isTreated).collect(Collectors.toList());
    }

    public Map<String, Long> getReclamationStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("Open", reclamationRepository.findAll().stream().filter(rec -> !rec.isTreated()).count());
        stats.put("Closed", reclamationRepository.findAll().stream().filter(Reclamation::isTreated).count());
        stats.put("CreatedToday", reclamationRepository.findAll().stream().filter(rec -> rec.getCreationDate().getDay()== new Date().getDay()).count());
        stats.put("Total", (long) reclamationRepository.findAll().size());
        return stats;
    }

    public Reclamation saveReclamation(Reclamation reclamation) {
        Long userId=extractUserIDFromToken();
        if (userId == null)
            throw new RuntimeException("No UserID");
        Users user = userrepository.findById(userId).orElse(null);
        if (user == null)
            throw new RuntimeException("No User");
        user.getReclamations().add(reclamation);
        reclamation.setClaimer(user);
        reclamation.setCreationDate(new Date());
        return reclamationRepository.save(reclamation);

    }

    public Reclamation closeReclamation(Long id, String solution) {
        Reclamation rec = reclamationRepository.findById(id).orElseThrow(RuntimeException::new);
        rec.setTreated(true);
        rec.setSolution(solution);
        reclamationRepository.save(rec);
        return rec;
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    private Long extractUserIDFromToken() {
//        log.info("extractUserIDFromToken");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Users) {
            Long userID = ((Users)principal).getId();
            return userID;
        }
        return null;


    }
}
