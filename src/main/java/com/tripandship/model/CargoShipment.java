package com.tripandship.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // ✨ Magic line: JSON fields mismatch hone par crash nahi hoga
public class CargoShipment {
    
    // Core Identifiers
    private String id;
    private String userId;
    
    // Shipment Details (Wizard Step 1 & 2)
    private String itemType;
    private double weight;
    private String origin;
    private String destination;
    private String cargoManifestNotes; // Extra details pipeline
    
    // Logistics & Insurance (Wizard Step 3)
    private String deliverySpeed;      // e.g., "Standard", "Express"
    private boolean insuranceApplied;
    
    // System Metadata (Wizard Step 4)
    private String status;
    private double cost;

    // Constructors
    public CargoShipment() {}

    public CargoShipment(String id, String userId, String itemType, double weight) {
        this.id = id;
        this.userId = userId;
        this.itemType = itemType;
        this.weight = weight;
    }

    // --- Getters and Setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getCargoManifestNotes() { return cargoManifestNotes; }
    public void setCargoManifestNotes(String cargoManifestNotes) { this.cargoManifestNotes = cargoManifestNotes; }

    public String getDeliverySpeed() { return deliverySpeed; }
    public void setDeliverySpeed(String deliverySpeed) { this.deliverySpeed = deliverySpeed; }

    public boolean isInsuranceApplied() { return insuranceApplied; }
    public void setInsuranceApplied(boolean insuranceApplied) { this.insuranceApplied = insuranceApplied; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}