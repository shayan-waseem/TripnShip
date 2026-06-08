package com.tripandship.patterns.state;

import com.tripandship.model.CargoShipment;

public class ConfirmedState implements CargoState {

    @Override
    public void next(CargoShipment cargo) {
        cargo.setState(new InTransitState());
    }

    @Override
    public void previous(CargoShipment cargo) {
        cargo.setState(new PendingState());
    }

    @Override
    public String getStatusString() {
        return "Confirmed";
    }
}