package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tripandship.model.CargoShipment;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoRepository {
    private final ObjectMapper mapper;
    private final String fileName = "cargoShipments.json";

    public CargoRepository() {
        this.mapper = new ObjectMapper();
        // Enable pretty printing for better readability of your JSON database
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<CargoShipment> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            // Handle cases where the JSON file is empty or null to prevent errors
            if (json == null || json.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return mapper.readValue(json, new TypeReference<List<CargoShipment>>() {});
        } catch (IOException e) {
            System.err.println("Error reading cargo shipments JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves or updates a cargo shipment entry inside the JSON database pipeline.
     * 🔐 Added 'synchronized' to prevent file corruption from concurrent write collisions.
     */
    public synchronized void save(CargoShipment shipment) {
        try {
            List<CargoShipment> shipments = findAll();
            
            // Safe Check & Clean Duplicate Remover: Aligned with BookingRepository strategy
            if (shipment.getId() != null) {
                shipments.removeIf(s -> s.getId() != null && s.getId().equalsIgnoreCase(shipment.getId().trim()));
            }
            
            // Append the new or modified shipment payload
            shipments.add(shipment);

            // Write the updated list back to the JSON file
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shipments);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            System.err.println("Error saving cargo shipment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}