package com.tripandship.patterns.command;

import com.tripandship.service.CargoService;

public class UpdateCargoStatusCommand implements AdminCommand {
    private final CargoService cargoService;
    private final String shipmentId;

    public UpdateCargoStatusCommand(CargoService cargoService, String shipmentId) {
        this.cargoService = cargoService;
        this.shipmentId = shipmentId;
    }

    @Override
    public void execute() {
        System.out.println("[AdminCommand] Advancing shipment status for: " + shipmentId);
        cargoService.advanceShipmentStatus(shipmentId);
    }
}
