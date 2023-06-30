package com.example.pidevback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pidevback.entities.Estate;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {
}
