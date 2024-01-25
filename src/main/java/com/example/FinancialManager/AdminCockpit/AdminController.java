package com.example.FinancialManager.AdminCockpit;

import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.userService.UserDetailsForm;
import com.example.FinancialManager.userService.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
public class AdminController {

    UserService userService;
    @GetMapping("/get-status")
    public boolean getStatus(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }

    @GetMapping("/get-all-users")
    public List<UserDetailsForm> getAllUsers(){
        return userService.getUsers();
    }
}
