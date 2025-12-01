package com.example.movie.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.model.User;
import com.example.movie.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @PreAuthorize("isAuthenticated()")
    public User updateUsername(long id, String newUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "User not found"));

        user.setUsername(newUsername);
        return userRepository.save(user);
    }
}
