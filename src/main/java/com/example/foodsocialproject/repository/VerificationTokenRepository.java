package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    @Query("SELECT p FROM VerificationToken p WHERE p.token = ?1 ")
    VerificationToken findByToken(String token);
}
