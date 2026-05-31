package com.tripandship.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tripandship.model.User;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAdvice {

    private final SessionService sessionService;

    public GlobalModelAdvice(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ModelAttribute("currentUser")
    public User currentUser(HttpSession session) {
        return sessionService.getCurrentUser(session).orElse(null);
    }
}
