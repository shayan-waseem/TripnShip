package com.tripandship.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.TravelPackage;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TravelService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "travelPackages.json";

    public List<TravelPackage> getAllPackages() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<TravelPackage>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void createPackage(TravelPackage travelPackage) {
        try {
            List<TravelPackage> list = getAllPackages();
            travelPackage.setId("PKG-" + (list.size() + 101));
            list.add(travelPackage);
            DatabaseManager.getInstance().writeData(fileName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}