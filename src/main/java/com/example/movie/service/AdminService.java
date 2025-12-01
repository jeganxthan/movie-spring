package com.example.movie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.model.User;
import com.example.movie.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
     private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllUsersAndResetId() {
        String sql = "TRUNCATE TABLE users RESTART IDENTITY CASCADE";
        jdbcTemplate.execute(sql);
    }

    public boolean deleteUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
