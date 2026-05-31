package com.tripandship.patterns.decorator;

import com.tripandship.model.CargoShipment;

public class ExpressDelivery extends ShipmentDecorator {
    public ExpressDelivery(CargoShipment baseShipment) {
        super(baseShipment);
    }

    @Override
    public double getCost() {
        return baseShipment.getCost() + 150.00; // Adds an explicit flat express delivery surcharge
    }
}