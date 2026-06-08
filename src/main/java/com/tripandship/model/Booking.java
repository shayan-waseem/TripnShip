package com.tripandship.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // ✨ MAGIC LINE: Yeh extra/unknown fields par crash hone se bachaegi!
public class Booking {
    
    private String id;
    private String userId;
    private String type;
    private String referenceId;
    private double price;
    private String status;
    private String phoneNumber;
    private String paymentMethod;
    private Integer passengerQty;
    private String packageTitle;
    private String origin;
    private String destination;
    private String username;
    
    // 🆕 New ticket metadata fields
    private String passengerName;
    private String bookingDate;
    private String travelDate;
    
    // Cargo specific fields
    private String itemType;
    private double weight;
    private String deliverySpeed;

    // 💡 Helper methods jo aapne controller/service me use kiye hain
    public boolean isTravel() {
        return "TRAVEL".equalsIgnoreCase(this.type) || "TRAVEL_PACKAGE".equalsIgnoreCase(this.type);
    }

    public boolean isCargo() {
        return "CARGO".equalsIgnoreCase(this.type);
    }

    // === SARE GETTERS AUR SETTERS ISKE NEECHE RAKHEIN ===
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Integer getPassengerQty() { return passengerQty; }
    public void setPassengerQty(Integer passengerQty) { this.passengerQty = passengerQty; }

    public String getPackageTitle() { return packageTitle; }
    public void setPackageTitle(String packageTitle) { this.packageTitle = packageTitle; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // 🆕 Getters and Setters for the new ticket metadata fields
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    // Cargo fields Getters and Setters
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getDeliverySpeed() { return deliverySpeed; }
    public void setDeliverySpeed(String deliverySpeed) { this.deliverySpeed = deliverySpeed; }
}