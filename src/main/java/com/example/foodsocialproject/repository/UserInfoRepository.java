package com.example.foodsocialproject.repository;

import com.example.foodsocialproject.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Long countById(UUID id);
}