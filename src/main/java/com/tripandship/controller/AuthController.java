package com.tripandship.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripandship.model.User;
import com.tripandship.service.AuthService;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final AuthService authService;
    private final SessionService sessionService;

    public AuthController(AuthService authService, SessionService sessionService) {
        this.authService = authService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        return sessionService.getCurrentUser(session)
                .map(u -> sessionService.isAdmin(u) ? "redirect:/admin/dashboard" : "redirect:/home")
                .orElse("login");
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        Optional<User> userOptional = authService.authenticate(username, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            sessionService.login(session, user);
            if (sessionService.isAdmin(user)) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/home";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.logout(session);
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignupPage(HttpSession session, Model model) {
        if (sessionService.getCurrentUser(session).isPresent()) {
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") User user, Model model) {
        user.setRole("USER");
        String error = authService.registerUser(user);
        if (error == null) {
            return "redirect:/login?registered=true";
        }
        model.addAttribute("error", error);
        return "signup";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "redirect:/signup";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user, Model model) {
        user.setRole("USER");
        String error = authService.registerUser(user);
        if (error == null) {
            return "redirect:/login?registered=true";
        }
        model.addAttribute("error", error);
        return "signup";
    }
}
