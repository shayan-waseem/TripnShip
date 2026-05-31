package com.tripandship.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    @GetMapping
    public String getWishlistView(Model model) {
        model.addAttribute("wishlistItems", new ArrayList<>());
        return "wishlist";
    }
}