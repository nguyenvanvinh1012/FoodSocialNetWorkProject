package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikesRepository  extends JpaRepository<Likes, UUID> {
    Long countById(UUID id);
}