package com.example.pidevback.repositories;

import com.example.pidevback.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLike,Long> {
}
