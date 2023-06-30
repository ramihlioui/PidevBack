package com.example.pidevback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pidevback.entities.Estate_Details;

@Repository
public interface EstateDetailsRepository extends JpaRepository<Estate_Details, Long> {
}
