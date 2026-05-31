package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationController {

    @GetMapping("/recommendations")
    public String showOffers() {
        return "redirect:/home";
    }
}
