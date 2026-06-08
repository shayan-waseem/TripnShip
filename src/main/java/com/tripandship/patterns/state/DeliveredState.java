package com.tripandship.patterns.state;

import com.tripandship.model.CargoShipment;

public class DeliveredState implements CargoState {

    @Override
    public void next(CargoShipment cargo) {
        System.out.println("Already Delivered");
    }

    @Override
    public void previous(CargoShipment cargo) {
        cargo.setState(new InTransitState());
    }

    @Override
    public String getStatusString() {
        return "Delivered";
    }
}