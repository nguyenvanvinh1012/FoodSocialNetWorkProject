package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostsRepository extends JpaRepository<Posts, UUID> {

    @Query("SELECT COUNT(*) FROM Posts")
    Long countPostsById(UUID userId);

    @Query("SELECT p FROM Posts p WHERE p.id =:id")
    Posts findByID(UUID id);
}
