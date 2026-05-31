package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tripandship.service.AnalyticsService;

@Controller
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/admin/analytics")
    public String viewMetrics(Model model) {
        model.addAllAttributes(analyticsService.getMetricsSummary());
        return "admin/analytics";
    }
}
