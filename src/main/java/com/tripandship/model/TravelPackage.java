package com.tripandship.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelPackage {

    private String id;
    private String title;
    private String description;
    private String origin;
    private String destination;
    private double price;
    private int availableSlots;
    private String airlineName;
    private String transitStops;
    private String cabinClass;
    private String flightDetails;
    private String hotelTier;
    private String additionalServices;

    private TravelPackage() {
    }

    private TravelPackage(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.price = builder.price;
        this.availableSlots = builder.availableSlots;
        this.airlineName = builder.airlineName;
        this.transitStops = builder.transitStops;
        this.cabinClass = builder.cabinClass;
        this.flightDetails = builder.flightDetails;
        this.hotelTier = builder.hotelTier;
        this.additionalServices = builder.additionalServices;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private String origin;
        private String destination;
        private double price;
        private int availableSlots;
        private String airlineName;
        private String transitStops;
        private String cabinClass;
        private String flightDetails;
        private String hotelTier;
        private String additionalServices;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder setBasePrice(double basePrice) {
            this.price = basePrice;
            return this;
        }

        public Builder setTotalSeats(int totalSeats) {
            this.availableSlots = totalSeats;
            return this;
        }

        public Builder setAirlineName(String airlineName) {
            this.airlineName = airlineName;
            return this;
        }

        public Builder setTransitStops(String transitStops) {
            this.transitStops = transitStops;
            return this;
        }

        public Builder setCabinClass(String cabinClass) {
            this.cabinClass = cabinClass;
            return this;
        }

        public Builder setFlightDetails(String flightDetails) {
            this.flightDetails = flightDetails;
            return this;
        }

        public Builder setHotelTier(String hotelTier) {
            this.hotelTier = hotelTier;
            return this;
        }

        public Builder setAdditionalServices(String additionalServices) {
            this.additionalServices = additionalServices;
            return this;
        }

        public TravelPackage build() {
            return new TravelPackage(this);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(int availableSlots) { this.availableSlots = availableSlots; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getTransitStops() { return transitStops; }
    public void setTransitStops(String transitStops) { this.transitStops = transitStops; }

    public String getCabinClass() { return cabinClass; }
    public void setCabinClass(String cabinClass) { this.cabinClass = cabinClass; }

    public String getFlightDetails() { return flightDetails; }
    public void setFlightDetails(String flightDetails) { this.flightDetails = flightDetails; }

    public String getHotelTier() { return hotelTier; }
    public void setHotelTier(String hotelTier) { this.hotelTier = hotelTier; }

    public String getAdditionalServices() { return additionalServices; }
    public void setAdditionalServices(String additionalServices) { this.additionalServices = additionalServices; }
}