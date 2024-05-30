package com.example.FinancialManager.userService;

import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("api/v1/profile/is-configured")
    public ResponseEntity<?> isAccountConfigured()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        boolean status;
        try {
            status = userService.getConfigurationStatus(email);
            return ResponseEntity.ok(status);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("api/v1/profile/get-user-role")
    public UserRole getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserData userDetails = (UserData) authentication.getPrincipal();
            return userDetails.getUserRole();
        }
        return null;
    }

    @GetMapping("/api/v1/profile/get-username")
    public ResponseEntity<String> getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserData userDetails = (UserData) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails.getUsername());
        }
        return null;
    }

    @PostMapping("api/v1/profile/set-configuration")
    public ResponseEntity<?> setUserConfiguration(@RequestBody ConfigurationForm configurationForm){
        try {
            if(userService.setUserConfiguration(configurationForm))
                userService.userConfiguredSet();
        }catch (HttpClientErrorException.BadRequest | IllegalAccessException e){
            return ResponseEntity.status(500).body("Bad request");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getConfigurationStatus(email));
    }

}
