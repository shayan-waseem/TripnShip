package com.tripandship.patterns.state;

import com.tripandship.model.CargoShipment;

public interface CargoState {

    void next(CargoShipment cargo);

    void previous(CargoShipment cargo);

    String getStatusString();
}