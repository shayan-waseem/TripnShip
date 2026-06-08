package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.Booking;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookingRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(BookingRepository.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "bookings.json";

    public List<Booking> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            if (json == null || json.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return mapper.readValue(json, new TypeReference<List<Booking>>() {});
        } catch (IOException e) {
            logger.error("Error reading booking data from file: {}", fileName, e);
            return new ArrayList<>();
        }
    }

    /**
     🆕 Added: Fetch booking item by String unique identifier reference
     */
    public Optional<Booking> findById(String id) {
        if (id == null) return Optional.empty();
        return findAll().stream()
                .filter(b -> b.getId() != null && b.getId().equalsIgnoreCase(id.trim()))
                .findFirst();
    }

    public List<Booking> findAllByUserAndType(String userId, String type) {
        if (userId == null || type == null) return new ArrayList<>();
        
        return findAll().stream()
                .filter(b -> b.getUserId() != null && userId.trim().equalsIgnoreCase(b.getUserId().trim()))
                .filter(b -> b.getType() != null && b.getType().toUpperCase().contains(type.toUpperCase()))
                .collect(Collectors.toList());
    }

    public synchronized void save(Booking booking) {
        try {
            List<Booking> bookings = findAll();
            
            if (booking.getId() == null || booking.getId().trim().isEmpty()) {
                String newId = generateNextId(bookings);
                booking.setId(newId);
            }
            
            bookings.removeIf(b -> b.getId() != null && b.getId().equals(booking.getId()));
            bookings.add(booking);
            
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
            DatabaseManager.getInstance().writeData(fileName, json);
            
            logger.info("Successfully saved booking ID: {} for User: {}", booking.getId(), booking.getUserId());
        } catch (Exception e) {
            logger.error("Critical error while saving booking data", e);
        }
    }

    private String generateNextId(List<Booking> bookings) {
        int maxId = bookings.stream()
                .map(Booking::getId) 
                .filter(id -> id != null && id.startsWith("BKG-")) 
                .map(id -> id.replaceAll("[^0-9]", "")) 
                .filter(num -> !num.isEmpty()) 
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(1000);
        return "BKG-" + (maxId + 1);
    }
}