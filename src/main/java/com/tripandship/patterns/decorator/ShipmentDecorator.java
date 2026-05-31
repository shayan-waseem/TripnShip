package com.tripandship.patterns.decorator;

import com.tripandship.model.CargoShipment;

public abstract class ShipmentDecorator extends CargoShipment {
    protected CargoShipment baseShipment;
    
    public ShipmentDecorator(CargoShipment baseShipment) {
        this.baseShipment = baseShipment;
    }
    
    @Override
    public abstract double getCost();
}