package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostsRepository extends JpaRepository<Posts, UUID> {
    Long countById(UUID id);
}
