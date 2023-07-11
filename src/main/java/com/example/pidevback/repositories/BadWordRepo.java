package com.example.pidevback.repositories;

import com.example.pidevback.entities.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordRepo extends JpaRepository<BadWord,Long> {
}
