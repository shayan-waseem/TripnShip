package com.tripandship.patterns.state;

import com.tripandship.model.CargoShipment;

public class InTransitState implements CargoState {

    @Override
    public void next(CargoShipment cargo) {
        cargo.setState(new DeliveredState());
    }

    @Override
    public void previous(CargoShipment cargo) {
        cargo.setState(new ConfirmedState());
    }

    @Override
    public String getStatusString() {
        return "In Transit";
    }
}