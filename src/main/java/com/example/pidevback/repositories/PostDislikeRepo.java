package com.example.pidevback.repositories;

import com.example.pidevback.entities.PostDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PostDislikeRepo extends JpaRepository<PostDislike,Long> {
}
