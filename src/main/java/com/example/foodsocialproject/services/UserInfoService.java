package com.example.foodsocialproject.services;

import com.example.foodsocialproject.entity.UserInfo;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoService implements TableService{
    private final UserInfoRepository userInfoRepository;
    @Override
    public List<UserInfo> getList() {
        return userInfoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Long count = userInfoRepository.countById(id);
        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Không tìm thấy ID: " + id);
        }
        userInfoRepository.deleteById(id);
    }

    @Override
    public Optional get(Long id) {
        Optional<UserInfo> result = userInfoRepository.findById(id);
        if (result.isPresent()){
            return result;
        }
        else
            throw new ResourceNotFoundException("Không tìm ID: " + id + "!");
    }
}
