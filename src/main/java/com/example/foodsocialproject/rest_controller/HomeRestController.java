package com.example.foodsocialproject.rest_controller;

import com.example.foodsocialproject.entity.Likes;
import com.example.foodsocialproject.entity.Posts;
import com.example.foodsocialproject.entity.Users;
import com.example.foodsocialproject.services.LikesService;
import com.example.foodsocialproject.services.PostsService;
import com.example.foodsocialproject.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeRestController {
    private final PostsService postsService;
    private final UserServices userServices;
    private final LikesService likeService;

    @GetMapping("/like")
    @ResponseBody
    public ResponseEntity<String> likePost(@RequestParam("id") String id, final HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        Users user = userServices.findbyEmail(userEmail).get();

        Posts post = postsService.findByID(UUID.fromString(id));
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        Likes check = null;
        for (Likes like : post.getLikes()) {
            if (like.getUser().getEmail().equals(user.getEmail())) {
                check = like;
            }
        }
        if (check == null) {
            // Tăng số lượt like lên 1
            post.setLikeCount(post.getLikeCount() + 1);
            Likes postLike = new Likes();
            postLike.setPost(post);
            postLike.setUser(user);
            likeService.save(postLike);
            postsService.save(post);
            System.out.println("LIKED");
            return ResponseEntity.ok("Liked");
        }
//unlike
            post.getLikes().remove(check);
            likeService.delete(check.getId());
            post.setLikeCount(post.getLikeCount() - 1);
            post.getSteps().remove(check);
            postsService.save(post);
            System.out.println("unliked");
            return ResponseEntity.ok("Unliked");

    }
}
