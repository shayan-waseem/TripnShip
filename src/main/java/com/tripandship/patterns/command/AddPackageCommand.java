package com.tripandship.patterns.command;

import com.tripandship.model.TravelPackage;
import com.tripandship.repository.TravelRepository;

public class AddPackageCommand implements AdminCommand {
    private final TravelRepository travelRepository;
    private final TravelPackage travelPackage;

    public AddPackageCommand(TravelRepository travelRepository, TravelPackage travelPackage) {
        this.travelRepository = travelRepository;
        this.travelPackage = travelPackage;
    }

    @Override
    public void execute() {
        System.out.println("[AdminCommand] Adding or updating travel package: " + travelPackage.getId());
        travelRepository.save(travelPackage);
    }
}
