package com.tripandship.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tripandship.model.TravelPackage;
import com.tripandship.repository.TravelRepository;

@Service
public class RecommendationService {
    private final TravelRepository travelRepository;

    public RecommendationService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public List<TravelPackage> getSmartRecommendations() {
        return travelRepository.findAll().stream().limit(2).collect(Collectors.toList());
    }
}