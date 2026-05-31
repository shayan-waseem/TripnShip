package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.Review;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "reviews.json";

    public List<Review> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<Review>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void save(Review review) {
        try {
            List<Review> list = findAll();
            review.setId("REV-" + (list.size() + 3001));
            list.add(review);
            DatabaseManager.getInstance().writeData(fileName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}