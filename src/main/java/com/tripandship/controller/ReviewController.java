package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tripandship.model.Review;
import com.tripandship.repository.ReviewRepository;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/add")
    public String submitReview(@ModelAttribute Review payload) {
        payload.setUserId("USR-002");
        reviewRepository.save(payload);
        return "redirect:/travel?reviewAdded=true";
    }
}