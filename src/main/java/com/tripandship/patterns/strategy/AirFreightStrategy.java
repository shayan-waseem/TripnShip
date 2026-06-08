package com.tripandship.patterns.strategy;

public class AirFreightStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        double base = weight * 6.0;
        double distanceFee = distance * 0.30;
        return Math.max(0, base + distanceFee + 120.0);
    }
}
