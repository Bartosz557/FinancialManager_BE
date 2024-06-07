package com.example.FinancialManager.API;

import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestDTO;
import com.example.FinancialManager.WebServices.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/profile")
public class UserController {

    private final UserService userService;

    @GetMapping("/is-configured")
    public ResponseEntity<?> isAccountConfigured()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(userService.getConfigurationStatus(authentication.getName()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/get-user-role")
    public UserRole getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserData userDetails = (UserData) authentication.getPrincipal();
            return userDetails.getUserRole();
        }
        return null;
    }

    @GetMapping("/get-username")
    public ResponseEntity<String> getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserData userDetails = (UserData) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails.getUsername());
        }
        return null;
    }

    @PostMapping("/set-configuration")
    public ResponseEntity<?> setUserConfiguration(@RequestBody ConfigurationRequestDTO configurationRequest){
        try {
            if(userService.setUserConfiguration(configurationRequest))
                userService.userConfiguredSet();
        }catch (HttpClientErrorException.BadRequest | IllegalAccessException e){
            return ResponseEntity.status(500).body("Bad request");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getConfigurationStatus(authentication.getName()));
    }
}
