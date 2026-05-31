package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final SessionService sessionService;

    public HomeController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        return sessionService.getCurrentUser(session)
                .map(user -> sessionService.isAdmin(user)
                        ? "redirect:/admin/dashboard"
                        : "redirect:/home")
                .orElse("redirect:/login");
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        sessionService.getCurrentUser(session).ifPresent(user -> model.addAttribute("user", user));
        return "home";
    }
}
