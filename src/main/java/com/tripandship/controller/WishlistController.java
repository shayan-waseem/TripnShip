package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tripandship.service.SessionService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class WishlistController {

    private final SessionService sessionService;
    // Assume aapke paas WishlistService hai jo wishlist.json read/write karti hai
    // private final WishlistService wishlistService;

    public WishlistController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // 1. Wishlist Page Render Pipeline
    @GetMapping("/wishlist")
    public String showWishlist(HttpSession session, Model model) {
        try {
            String userId = sessionService.requireUserId(session);
            if (userId == null) return "redirect:/login";

            // List<TravelPackage> savedItems = wishlistService.getWishlistForUser(userId);
            model.addAttribute("wishlistItems", new ArrayList<>()); // Apni actual list se replace karein
        } catch (Exception e) {
            model.addAttribute("wishlistItems", new ArrayList<>());
        }
        return "wishlist";
    }

    // 2. Async API Endpoint background storage ke liye (wishlist.json handle karne ke liye)
    @PostMapping("/api/wishlist/toggle")
    @ResponseBody
    public org.springframework.http.ResponseEntity<String> toggleWishlistItem(
            @RequestParam String packageId, HttpSession session) {
        try {
            String userId = sessionService.requireUserId(session);
            if (userId == null) return org.springframework.http.ResponseEntity.status(401).build();

            // wishlistService.toggleItemInJson(userId, packageId); // json update trigger yahan hoga
            return org.springframework.http.ResponseEntity.ok("Wishlist updated successfully");
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(500).build();
        }
    }
}