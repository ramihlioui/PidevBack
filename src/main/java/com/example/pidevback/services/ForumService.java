package com.example.pidevback.services;

import com.example.pidevback.entities.BadWord;
import com.example.pidevback.entities.Post;
import com.example.pidevback.entities.PostDislike;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ForumService {

    @Autowired
    UserService userService;
    @Autowired
    PostRepo postRepo;
    @Autowired
    PostLikeRepo postLikeRepo;
    @Autowired
    PostDislikeRepo postDislikeRepo;
    @Autowired
    PostCommentRepo postCommentRepo;
    @Autowired
    CommentLikeRepo commentLikeRepo;
    @Autowired
    BadWordRepo badWordRepo;
    @Autowired
    UserRepository userRepository;
    //Add Post//
    public ResponseEntity<?> addPost(Post post, Long IdUser){

        Users u = userRepository.findById(IdUser).orElse(null);
        if(Filtrage_bad_word(post.getBody())==0 && Filtrage_bad_word(post.getPostTitle())==0){
            post.setUsers(u);
            post.setCreatedAt(new Date());
            u.getPosts().add(post);
            postRepo.save(post);
            return  ResponseEntity.ok().body(post);
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Offensive Language is Detected");
    }
    //Add Post//

    //Update post//
    public ResponseEntity<?> editPost(Post post,Long idUser) {
        Post p = postRepo.findById(post.getPostId()).orElseThrow(EntityNotFoundException::new);
        Users user = userRepository.findById(p.getUsers().getId()).orElseThrow(EntityNotFoundException::new);
        if (user.getId() == idUser) {
            p.setPostTitle(post.getPostTitle());
            p.setBody(post.getBody());
            postRepo.save(p);
            return  ResponseEntity.ok().body(p);
        }else {
                    return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Post Not Found");
                }
    }
    //Update post//


    //Delete Post//

    public ResponseEntity<?> Delete_post(Long idPost , Long idUser){
        if(postRepo.existsById(idPost)){
            Post post= postRepo.findById(idPost).orElseThrow(EntityNotFoundException::new);
            Users user=userRepository.findById(post.getUsers().getId()).orElseThrow(EntityNotFoundException::new);
            if(user.getId()==idUser){
                user.getPosts().remove(post);
//                userRepository.save(user);
                postRepo.delete(post);
                return  ResponseEntity.ok().body("Post Deleted");
            }else{
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("you can't delete this post");
            }

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Not Found");
        }
    }

    //Delete Post//

    //Get All posts//
    public List<Post> Get_all_posts(){
        return postRepo.findAll();
    }
    //Get All posts//


    //Get post bu user//
    public Set<Post> get_post_by_user(Long idUser){
        return userRepository.findById(idUser).orElseThrow().getPosts();
    }
    //Get post bu user//






    //bad words filter //
    public BadWord addBadWord(BadWord b ) {

        return badWordRepo.save(b);
    }

    public int Filtrage_bad_word(String ch) {
        int x = 0;
        List<BadWord> l1 = (List<BadWord>) badWordRepo.findAll();
        for (BadWord badWord : l1) {
            if (ch.contains(badWord.getWord()) == true)
                x = 1;
        }
        return x;

    }
    //bad words filter //
}
