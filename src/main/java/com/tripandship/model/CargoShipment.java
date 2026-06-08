package com.tripandship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripandship.patterns.state.CargoState;
import com.tripandship.patterns.state.CargoStateFactory;
import com.tripandship.patterns.state.PendingState;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CargoShipment {

    @JsonIgnore
    private CargoState state;
    private String id;
    private String userId;
    private String itemType;
    private double weight;
    private String origin;
    private String destination;
    private String cargoManifestNotes;
    private String deliverySpeed;
    private String shippingMethod;
    private boolean insuranceApplied;
    private String paymentMethod;
    private String cardHolderName;
    private String expiryDate;

    @JsonIgnore
    private String cardNumber;

    @JsonIgnore
    private String cvv;

    private String status;
    private double cost;

    public CargoShipment() {
        this.state = new PendingState();
        this.status = "Pending";
        this.shippingMethod = "Air Freight";
    }

    public CargoShipment(String id, String userId, String itemType, double weight) {
        this.id = id;
        this.userId = userId;
        this.itemType = itemType;
        this.weight = weight;
        this.state = new PendingState();
        this.status = "Pending";
        this.shippingMethod = "Air Freight";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCargoManifestNotes() {
        return cargoManifestNotes;
    }

    public void setCargoManifestNotes(String cargoManifestNotes) {
        this.cargoManifestNotes = cargoManifestNotes;
    }

    public String getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(String deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public boolean isInsuranceApplied() {
        return insuranceApplied;
    }

    public void setInsuranceApplied(boolean insuranceApplied) {
        this.insuranceApplied = insuranceApplied;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if (status != null) {
            this.state = CargoStateFactory.fromString(status);
        }
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
        this.status = state.getStatusString();
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.previous(this);
    }
}
