package com.tripandship.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tripandship.model.User;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    public AuthInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = request.getRequestURI();

        if (isPublic(path) || path.startsWith("/error")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        var userOptional = sessionService.getCurrentUser(session);

        if (userOptional.isEmpty()) {
            response.sendRedirect("/login");
            return false;
        }

        User user = userOptional.get();
        if (path.startsWith("/admin") && !sessionService.isAdmin(user)) {
            response.sendRedirect("/home");
            return false;
        }

        if ((path.equals("/home") || path.equals("/login")) && sessionService.isAdmin(user)) {
            response.sendRedirect("/admin/dashboard");
            return false;
        }

        return true;
    }

    private boolean isPublic(String path) {
        return path.equals("/login")
                || path.equals("/signup")
                || path.equals("/register")
                || path.startsWith("/login")
                || path.startsWith("/signup")
                || path.startsWith("/register");
    }
}
