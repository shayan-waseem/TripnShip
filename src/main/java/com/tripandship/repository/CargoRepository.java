package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.CargoShipment;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "cargoShipments.json";

    public List<CargoShipment> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<CargoShipment>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void save(CargoShipment shipment) {
        try {
            List<CargoShipment> shipments = findAll();
            shipments.add(shipment);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shipments);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}