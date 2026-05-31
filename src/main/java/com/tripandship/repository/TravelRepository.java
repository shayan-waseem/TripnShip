package com.tripandship.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.model.TravelPackage;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TravelRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "travelPackages.json";

    public List<TravelPackage> findAll() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<List<TravelPackage>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Optional<TravelPackage> findById(String id) {
        return findAll().stream().filter(pkg -> pkg.getId().equalsIgnoreCase(id)).findFirst();
    }

    /**
     * 🆕 Custom Method to filter travel packages by Origin and Destination from JSON structure.
     * Case-insensitive check lagaya hai taake typing mistake se mismatch na ho.
     */
    public List<TravelPackage> findByOriginAndDestination(String origin, String destination) {
        return findAll().stream()
                .filter(pkg -> pkg.getOrigin() != null && pkg.getOrigin().equalsIgnoreCase(origin)
                        && pkg.getDestination() != null && pkg.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }
}