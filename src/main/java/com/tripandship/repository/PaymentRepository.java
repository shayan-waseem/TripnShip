package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.Payment;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "payments.json";

    public List<Payment> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<Payment>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void save(Payment payment) {
        try {
            List<Payment> items = findAll();
            payment.setId("PMT-" + (items.size() + 2001));
            items.add(payment);
            DatabaseManager.getInstance().writeData(fileName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}