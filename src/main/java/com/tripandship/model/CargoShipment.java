package com.tripandship.model;

public class CargoShipment {
    private String id;
    private String userId;
    private String itemType;
    private double weight;
    private String origin;
    private String destination;
    private String status;
    private String deliverySpeed;
    private double cost;
    private boolean insuranceApplied;

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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeliverySpeed() { return deliverySpeed; }
    public void setDeliverySpeed(String deliverySpeed) { this.deliverySpeed = deliverySpeed; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public boolean isInsuranceApplied() { return insuranceApplied; }
    public void setInsuranceApplied(boolean insuranceApplied) { this.insuranceApplied = insuranceApplied; }
}