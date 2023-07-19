package com.example.pidevback.controllers;


import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.Users;
import com.example.pidevback.services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/reclamation")
public class ReclamationController {
    @Autowired
    ReclamationService reclamationService;

    @PostMapping("/addReclamation/{userid}")
    public ResponseEntity<Reclamation> addComplaint(@RequestBody Reclamation reclamation, Users user) {
        Long iduser = user.getId();
        Reclamation rec = reclamationService.saveReclamation(reclamation, iduser);
        return ResponseEntity.status(HttpStatus.CREATED).body(rec);
    }

    @PutMapping("close/{complaintId}")
    public ResponseEntity<Reclamation> closeReclamation(@RequestBody String solution, @PathVariable Long complaintId) {
        Reclamation rec = reclamationService.closeReclamation(complaintId, solution);
        return ResponseEntity.ok(rec);
    }

    @DeleteMapping("delete/{complaintId}")
    @ResponseBody
    public ResponseEntity<Reclamation> deleteReclamation(@PathVariable Long complaintId) {
        reclamationService.deleteReclamation(complaintId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getReclamation/{complaintId}")
    public ResponseEntity<Reclamation> getReclamation(@PathVariable Long complaintId) {
        Reclamation rec = reclamationService.getReclamation(complaintId);
        return ResponseEntity.ok(rec);
    }

    @GetMapping("/affichallReclamations")
    public ResponseEntity<List<Reclamation>> getAllReclamation() {
        List<Reclamation> rec = reclamationService.getReclamations();
        return ResponseEntity.ok(rec);
    }

    @GetMapping("/afficherOpenReclamations")
    public ResponseEntity<List<Reclamation>> getOpenReclamation() {
        List<Reclamation> rec = reclamationService.getOpenReclamations();
        return ResponseEntity.ok(rec);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getReclamationStats() {

        Map<String, Long> stats = reclamationService.getReclamationStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/historique")
    public ResponseEntity<List<Reclamation>> historique() {

        List<Reclamation> rec = reclamationService.getClosedReclamations();
        return ResponseEntity.ok(rec);
    }

}
