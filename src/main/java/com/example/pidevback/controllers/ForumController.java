package com.example.pidevback.controllers;


import antlr.Token;
import com.example.pidevback.entities.Post;
import com.example.pidevback.entities.Users;
import com.example.pidevback.services.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@Slf4j
@RequestMapping("/post")
public class ForumController {
    @Autowired
    ForumService forumService;


    @PostMapping("/")
    public ResponseEntity<?> addPost(@RequestBody Post post){
       // Logger.getLogger("token-------->",Token);
        Long userID=extractUserIDFromToken();

        log.info("user id --------------------------------------------->"+userID);
        return forumService.addPost(post,userID);
    }

    @PutMapping("/")
    public ResponseEntity<?> editPost(@RequestBody Post post){
        Long userID=extractUserIDFromToken();
        log.info("user id --------------------------------------------->"+userID);
        return forumService.editPost(post,userID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        Long userID=extractUserIDFromToken();
        log.info("user id --------------------------------------------->"+userID);
        return forumService.Delete_post(id,userID);
    }

    @GetMapping
    public List<Post> Get_all_posts(){
        return forumService.Get_all_posts();
    }
    @GetMapping("/{id}")
    public List<Post> Get_Post(@PathVariable Long id){
            return forumService.Get_all_posts();
    }

    private Long extractUserIDFromToken() {
        log.info("extractUserIDFromToken");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Users) {
            Long userID = ((Users)principal).getId();
            return userID;
        }
           return null;


    }

}
