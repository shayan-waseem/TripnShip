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
            if (json == null || json.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return mapper.readValue(json, new TypeReference<List<TravelPackage>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Optional<TravelPackage> findById(String id) {
        return findAll().stream().filter(pkg -> pkg.getId().equalsIgnoreCase(id)).findFirst();
    }

    public List<TravelPackage> findByOriginAndDestination(String origin, String destination) {
        return findAll().stream()
                .filter(pkg -> pkg.getOrigin() != null && pkg.getOrigin().equalsIgnoreCase(origin)
                        && pkg.getDestination() != null && pkg.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    /**
     Persistence Node: Saves or updates packages inside JSON cluster.
     */
    public synchronized void save(TravelPackage travelPackage) {
        try {
            List<TravelPackage> packages = findAll();
            
            // Duplicate Remover Logic
            packages.removeIf(pkg -> pkg.getId() != null && pkg.getId().equalsIgnoreCase(travelPackage.getId()));
            
            packages.add(travelPackage);
            
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(packages);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            System.err.println("Error committing package node: " + e.getMessage());
        }
    }

    /**
     Purge Node: Deletes package by ID string from JSON stream.
     */
    public synchronized void deleteById(String id) {
        try {
            List<TravelPackage> packages = findAll();
            packages.removeIf(pkg -> pkg.getId() != null && pkg.getId().equalsIgnoreCase(id));
            
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(packages);
            DatabaseManager.getInstance().writeData(fileName, json);
        } catch (IOException e) {
            System.err.println("Error purging package node: " + e.getMessage());
        }
    }
}