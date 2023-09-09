package com.example.foodsocialproject.services;

import com.example.foodsocialproject.entity.Posts;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostsService implements TableService{
    private final PostsRepository postsRepository;

    @Override
    public List<Posts> getList() {
        return postsRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        Long count = postsRepository.countPostsById(id);
        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Không tìm thấy ID: " + id);
        }
        postsRepository.deleteById(id);
    }

    @Override
    public Optional get(UUID id) {
        Optional<Posts> result = postsRepository.findById(id);
        if (result.isPresent()){
            return result;
        }
        else
            throw new ResourceNotFoundException("Không tìm ID: " + id + "!");
    }
}
