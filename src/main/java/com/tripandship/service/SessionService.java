package com.tripandship.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tripandship.config.SessionKeys;
import com.tripandship.model.User;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    public void login(HttpSession session, User user) {
        session.setAttribute(SessionKeys.CURRENT_USER, user);
    }

    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public Optional<User> getCurrentUser(HttpSession session) {
        if (session == null) {
            return Optional.empty();
        }
        Object value = session.getAttribute(SessionKeys.CURRENT_USER);
        if (value instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public boolean isAdmin(User user) {
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public String requireUserId(HttpSession session) {
        return getCurrentUser(session)
                .map(User::getId)
                .orElse("USR-002");
    }
}
