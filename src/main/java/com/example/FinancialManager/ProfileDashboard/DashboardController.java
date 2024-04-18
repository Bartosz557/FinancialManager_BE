package com.example.FinancialManager.ProfileDashboard;

import com.example.FinancialManager.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/profile/dashboard")
public class DashboardController {

    DashboardUserService dashboardUserService;
    UserService userService;
    @GetMapping("/get-data/main-page")
    public ResponseEntity<?> getMainPageData(){
        try {
            return ResponseEntity.ok(dashboardUserService.getUserData());
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.internalServerError().body("Cannot fetch data");
        }
    }

    @PostMapping("/add-transaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionForm transactionForm) {
        try {
            return ResponseEntity.ok(userService.addTransaction(transactionForm));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
