package com.tripandship.patterns.strategy;

public class OceanCargoStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        double base = weight * 3.5;
        double distanceFee = distance * 0.18;
        return Math.max(0, base + distanceFee + 80.0);
    }
}
