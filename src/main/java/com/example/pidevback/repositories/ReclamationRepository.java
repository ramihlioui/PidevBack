package com.example.pidevback.repositories;

import com.example.pidevback.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamationRepository  extends JpaRepository<Reclamation, Long> {
}
