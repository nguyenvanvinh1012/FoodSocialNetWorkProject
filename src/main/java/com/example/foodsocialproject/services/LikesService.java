package com.example.foodsocialproject.services;

import com.example.foodsocialproject.entity.Likes;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikesService implements TableService{
    private final LikesRepository likesRepository;
    @Override
    public List<Likes> getList() {
        return likesRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        Long count = likesRepository.countById(id);
        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Không tìm thấy ID: " + id);
        }
        likesRepository.deleteById(id);
    }

    @Override
    public Optional get(UUID id) {
        Optional<Likes> result = likesRepository.findById(id);
        if (result.isPresent()){
            return result;
        }
        else
            throw new ResourceNotFoundException("Không tìm ID: " + id + "!");
    }
}
