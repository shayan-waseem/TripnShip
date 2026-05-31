package com.tripandship.service;

import com.tripandship.model.CargoShipment;
import com.tripandship.patterns.decorator.ExpressDelivery;
import com.tripandship.patterns.decorator.InsuranceDecorator;
import com.tripandship.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoShipment> getAllShipments() {
        return cargoRepository.findAll();
    }

    public CargoShipment registerShipment(CargoShipment shipment) {
        shipment.setId("SHP-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        
        // Base Cost derived dynamically: $10 per unit weight item unit scale
        double calculatedBase = shipment.getWeight() * 10.0;
        shipment.setCost(calculatedBase);

        // Decorator processing layer matches options chosen in the UI
        if ("Express".equalsIgnoreCase(shipment.getDeliverySpeed())) {
            shipment.setCost(new ExpressDelivery(shipment).getCost());
        }
        if (shipment.isInsuranceApplied()) {
            shipment.setCost(new InsuranceDecorator(shipment).getCost());
        }

        shipment.setStatus("In Transit");
        cargoRepository.save(shipment);
        return shipment;
    }
}