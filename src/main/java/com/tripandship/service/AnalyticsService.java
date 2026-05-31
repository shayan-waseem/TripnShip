package com.tripandship.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripandship.patterns.observer.Observer;
import com.tripandship.patterns.observer.Subject;
import com.tripandship.patterns.singleton.DatabaseManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName = "analytics.json";

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, Object data) {
        for (Observer observer : observers) {
            observer.update(eventType, data);
        }
    }

    public Map<String, Object> getMetricsSummary() {
        try {
            String json = DatabaseManager.getInstance().readData(fileName);
            return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            Map<String, Object> defaultMap = new HashMap<>();
            defaultMap.put("totalRevenue", 3075.00);
            defaultMap.put("totalBookingsCount", 3);
            defaultMap.put("totalShipmentsCount", 1);
            return defaultMap;
        }
    }

    public void logSystemActivity(String eventType, double transactionValue) {
        try {
            Map<String, Object> metrics = getMetricsSummary();
            
            double currentRevenue = ((Number) metrics.getOrDefault("totalRevenue", 0.0)).doubleValue();
            int bookingsCount = ((Number) metrics.getOrDefault("totalBookingsCount", 0)).intValue();
            int shipmentsCount = ((Number) metrics.getOrDefault("totalShipmentsCount", 0)).intValue();

            if ("TRAVEL_BOOKING".equalsIgnoreCase(eventType)) {
                metrics.put("totalRevenue", currentRevenue + transactionValue);
                metrics.put("totalBookingsCount", bookingsCount + 1);
            } else if ("CARGO_SHIPMENT".equalsIgnoreCase(eventType)) {
                metrics.put("totalRevenue", currentRevenue + transactionValue);
                metrics.put("totalShipmentsCount", shipmentsCount + 1);
            }

            String updatedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(metrics);
            DatabaseManager.getInstance().writeData(fileName, updatedJson);

            // Notify all operational observers at runtime
            notifyObservers(eventType, metrics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}