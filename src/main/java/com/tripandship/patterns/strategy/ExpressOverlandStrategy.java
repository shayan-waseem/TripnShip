package com.tripandship.patterns.strategy;

public class ExpressOverlandStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        double base = weight * 5.0;
        double distanceFee = distance * 0.25;
        return Math.max(0, base + distanceFee + 140.0);
    }
}
