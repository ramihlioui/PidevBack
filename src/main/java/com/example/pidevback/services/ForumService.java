package com.example.pidevback.services;

import com.example.pidevback.entities.*;
import com.example.pidevback.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Slf4j
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
    public void addBadWord(BadWord b ) {
        BadWord b1= new BadWord();
        b1.setWord(b.getWord());
        log.info(b1.getWord());
         badWordRepo.save(b1);

    }

    public int Filtrage_bad_word(String ch) {
        int x = 0;
        List<BadWord> l1 = (List<BadWord>) badWordRepo.findAll();

        for (BadWord badWord : l1) {
            log.info("wooooooooooooooooooooooooooord-------------------------->"+badWord);
            if (ch.contains(badWord.getWord()) == true)
                x = 1;
        }
        return x;

    }
    //bad words filter //

    public ResponseEntity<?> addComment_to_Post(PostComment postComment, Long idPost, Long idUser) {
        Post p = postRepo.findById(idPost).orElse(null);
        Users u = userRepository.findById(idUser).orElse(null);
        if (Filtrage_bad_word(postComment.getCommentBody()) == 0) {
            postComment.setCommentedAt(new Date());
            postComment.setUser(u);
            postComment.setPost(p);

            postCommentRepo.save(postComment);
            return ResponseEntity.ok().body(postComment);
        }else

            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Bads Word Detected");
    }

    public ResponseEntity<?> addLike_to_Post(PostLike postLike, Long idPost, Long idUser) {
        int x=0;
        boolean y =false;
        Post p = postRepo.findById(idPost).orElse(null);
        Users u = userRepository.findById(idUser).orElse(null);
        for (PostLike l : postLikeRepo.findAll()) {
            if(l.getPost().getPostId() == idPost && l.getUser().getId() == idUser)
            {
                x=1;
                y=l.getIsLiked();
                postLikeRepo.delete(l);

            }

        }
         for (PostDislike l1 : postDislikeRepo.findAll()) {
            if(l1.getPost().getPostId() == idPost && l1.getUser().getId() == idUser)
            {
                postDislikeRepo.delete(l1);

            }

        }
        if (x ==0 || (x == 1 && y!=postLike.getIsLiked()	)) {
            postLike.setUser(u);
            postLike.setPost(p);
            postLikeRepo.save(postLike);}

        return  ResponseEntity.ok().body(postLike);
    }

    public CommentLike addLike_to_Comment(CommentLike commentLike, Long idComment, Long idUser) {
        Users u = userRepository.findById(idUser).orElse(null);
        PostComment p = postCommentRepo.findById(idComment).orElse(null);
        commentLike.setUser(u);
        commentLike.setPostComment(p);
        return commentLikeRepo.save(commentLike);
    }
    public ResponseEntity<?> add_Com_to_Com(PostComment postComment, Long idUser, Long idCom) {
        PostComment p = postCommentRepo.findById(idCom).orElse(null);
        Users u = userRepository.findById(idUser).orElse(null);
        if (Filtrage_bad_word(postComment.getCommentBody()) == 0) {
            postComment.setUser(u);
            //	postComment.setPost(p.getPost());
            //	p.getPostComments().add(postComment);
            postComment.setPostReply(p);
            postCommentRepo.save(postComment);
            return ResponseEntity.ok().body(postComment);

        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Offensive Language Detected");
    }
    @Scheduled(cron = "*/30 * * * * *")
    public void Delete_Dead_Posts() {
        for (Post p : postRepo.findAll()) {
            if (postRepo.diffrence_entre_date(p.getCreatedAt())>30) {
                if (p.getPostLikes().size() == 0) {
                    Delete_post(p.getPostId(), p.getUsers().getId());
//                    System.out.println("Post with id = "+ p.getPostId() +" deleted");
                }
            }
        }
//        System.out.println("Testing for post with no interraction");
    }

}


