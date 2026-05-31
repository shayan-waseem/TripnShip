package com.tripandship.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tripandship.model.User;
import com.tripandship.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** @return null on success, or an error message to show on the form */
    public String registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return "Username is required.";
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username is already taken.";
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()
                && userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email is already registered.";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Password must be at least 6 characters.";
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        userRepository.save(user);
        return null;
    }

    public Optional<User> authenticate(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }
        String u = username.trim();
        String p = password.trim();
        return userRepository.findByUsername(u)
                .filter(user -> user.getPassword() != null && user.getPassword().equals(p));
    }
}