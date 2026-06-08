package com.tripandship.patterns.command;

import com.tripandship.repository.TravelRepository;

public class DeletePackageCommand implements AdminCommand {
    private final TravelRepository travelRepository;
    private final String packageId;

    public DeletePackageCommand(TravelRepository travelRepository, String packageId) {
        this.travelRepository = travelRepository;
        this.packageId = packageId;
    }

    @Override
    public void execute() {
        System.out.println("[AdminCommand] Deleting travel package: " + packageId);
        travelRepository.deleteById(packageId);
    }
}
