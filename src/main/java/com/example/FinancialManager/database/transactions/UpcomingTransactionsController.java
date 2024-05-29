package com.example.FinancialManager.database.transactions;

import com.example.FinancialManager.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/upcoming-payments")
public class UpcomingTransactionsController {

    UserService userService;
    @GetMapping("/get-data")
    public ResponseEntity<?> getUpcomingPayments(){
        try {
            return ResponseEntity.ok(userService.getAllUpcomingPayments());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
