package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.User;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "users.json";

    public List<User> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public void save(User user) {
        try {
            List<User> users = findAll();
            user.setId("USR-" + (users.size() + 101));
            users.add(user);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}