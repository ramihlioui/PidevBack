package com.example.pidevback.repositories;

import com.example.pidevback.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PostCommentRepo extends JpaRepository<PostComment,Long> {
}
