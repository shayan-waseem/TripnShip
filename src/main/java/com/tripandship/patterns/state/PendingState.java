package com.tripandship.patterns.state;

import com.tripandship.model.CargoShipment;

public class PendingState implements CargoState {

    @Override
    public void next(CargoShipment cargo) {
        cargo.setState(new ConfirmedState());
    }

    @Override
    public void previous(CargoShipment cargo) {
        System.out.println("Already in Pending state");
    }

    @Override
    public String getStatusString() {
        return "Pending";
    }
}