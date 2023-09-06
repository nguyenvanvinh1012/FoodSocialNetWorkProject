package com.example.foodsocialproject.services;

import com.example.foodsocialproject.entity.Users;
import com.example.foodsocialproject.exception.ResourceNotFoundException;
import com.example.foodsocialproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServices implements TableService{
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Users> getList() {
        return userRepository.findAll();
    }

    public Optional<Users> findByEmail(String email) {
        Optional<Users> result = userRepository.findByEmail(email);
        if (result.isPresent()){
            return result;
        }
        else return null;
    }

    public void save(Users user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new ResourceNotFoundException("Người dùng với email " + user.getEmail() + " đã tồn tại");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void delete(UUID id) {
        Long count = userRepository.countById(id);
        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional get(UUID id) {
        Optional<Users> result = userRepository.findById(id);
        if (result.isPresent()){
            return result;
        }
        else
            throw new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id + "!");
    }

}
