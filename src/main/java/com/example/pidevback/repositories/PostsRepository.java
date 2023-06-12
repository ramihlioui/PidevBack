package com.example.pidevback.repositories;

import com.example.pidevback.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {
}
