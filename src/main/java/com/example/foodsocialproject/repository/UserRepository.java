package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Long countById(Long id);

    Optional<Users> findByEmail(String email);
}