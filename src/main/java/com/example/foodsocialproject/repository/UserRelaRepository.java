package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.UserRela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRelaRepository extends JpaRepository<UserRela, UUID> {
    Long countById(UUID id);
}
