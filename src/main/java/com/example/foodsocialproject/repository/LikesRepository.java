package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikesRepository  extends JpaRepository<Likes, Long> {
    Long countById(Long id);
}