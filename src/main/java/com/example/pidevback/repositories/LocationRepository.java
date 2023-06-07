package com.example.pidevback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pidevback.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
