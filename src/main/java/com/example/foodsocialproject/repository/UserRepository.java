package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Long countById(UUID id);

    Optional<Users> findByEmail(String email);
}