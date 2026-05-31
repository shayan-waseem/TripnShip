package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.Booking;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "bookings.json";

    public List<Booking> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<Booking>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void save(Booking booking) {
        try {
            List<Booking> bookings = findAll();
            booking.setId("BKG-" + (bookings.size() + 1001));
            bookings.add(booking);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}