package com.example.FinancialManager.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @GetMapping("/api/v1/logout")
    public boolean logout() {
        SecurityContextHolder.clearContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return !(authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("USER")));
        }
        return true;
    }
}
