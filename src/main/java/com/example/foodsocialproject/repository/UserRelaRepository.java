package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.UserRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRelaRepository extends JpaRepository<UserRela, UUID> {
    Long countById(UUID id);
}
