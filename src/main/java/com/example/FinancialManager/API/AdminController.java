package com.example.FinancialManager.API;

import com.example.FinancialManager.DTO.RequestBody.SettingChangeRequestDTO;
import com.example.FinancialManager.WebServices.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    UserService userService;

    @GetMapping("/get-status")
    public boolean getStatus(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }

    @GetMapping("/get-all-users")
    public String getAllUsers(){
        return userService.getUsers();
    }

    @PutMapping("/change-user-settings/change-username")
    public ResponseEntity<?> changeUsername(@RequestBody SettingChangeRequestDTO request){
        try {
            userService.updateUserSetting(request, "username");
            return ResponseEntity.ok("Username changed successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

    // TODO: stick it into 1 controller method!!
    @PutMapping("/change-user-settings/change-email")
    public ResponseEntity<?> changeEmail(@RequestBody SettingChangeRequestDTO request){
        try {
            userService.updateUserSetting(request, "email");
            return ResponseEntity.ok("Email changed successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }
    @PutMapping("/change-user-settings/change-paswword")
    public ResponseEntity<?> changePassword(@RequestBody SettingChangeRequestDTO request){
        try {
            userService.updateUserSetting(request, "password");
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete-user/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        logger.info("Deleting user account: " + username);
        try {
            int a = userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully: " + a);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }


    @DeleteMapping("/delete-transaction/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int transactionId){
        try {
            String response = userService.deleteTransaction(transactionId);
            return ResponseEntity.ok("Transaction deleted successfully: " + response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

}
