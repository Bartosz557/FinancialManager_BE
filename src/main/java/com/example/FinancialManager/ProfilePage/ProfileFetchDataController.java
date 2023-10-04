package com.example.FinancialManager.ProfilePage;

import com.example.FinancialManager.User.AppUser;
import com.example.FinancialManager.User.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileFetchDataController {

    @GetMapping("/api/v1/fetchdata")
    public String fetchData()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // You can access various user details from the authentication object
        String username = authentication.getName(); // Get the username
        boolean isAuthenticated = authentication.isAuthenticated(); // Check if the user is authenticated
        String a="";
        // You can also access custom user details if they are available in the authentication object
        // For example, if you have a custom UserDetails implementation
        if (authentication.getPrincipal() instanceof AppUser) {
            AppUser customUserDetails = (AppUser) authentication.getPrincipal();
            a = customUserDetails.getFirstName();
        }

        // You can return the user data as JSON or in any other desired format
        return username + "data: " + a;
    }
}
