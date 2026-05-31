package com.tripandship.patterns.decorator;

import com.tripandship.model.CargoShipment;

public class InsuranceDecorator extends ShipmentDecorator {
    public InsuranceDecorator(CargoShipment baseShipment) {
        super(baseShipment);
    }

    @Override
    public double getCost() {
        return baseShipment.getCost() + 50.00; // Flat-rate asset insurance coverage fee
    }
}