package com.example.FinancialManager.ProfilePage;

import com.example.FinancialManager.User.AppUser;
import com.example.FinancialManager.User.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  ProfileFetchDataController {

    @GetMapping("/api/v1/fetchdata")
    public String fetchData()
    {
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AppUser) {
            AppUser customUserDetails = (AppUser) authentication.getPrincipal();
            username = customUserDetails.getFirstName();
        }
        return username;
    }
}
