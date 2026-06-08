package com.tripandship.patterns.strategy;

public interface ShippingStrategy {
    double calculateCost(double weight, double distance);
}
