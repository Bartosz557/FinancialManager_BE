package com.example.FinancialManager.API;

import com.example.FinancialManager.WebServices.DashboardUserService;
import com.example.FinancialManager.DTO.RequestBody.TransactionRequestDTO;
import com.example.FinancialManager.WebServices.TransactionService;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.ExpenseModelDTO;
import com.example.FinancialManager.WebServices.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/profile/dashboard")
public class DashboardController {

    DashboardUserService dashboardUserService;
    UserService userService;
    TransactionService transactionService;
    @GetMapping("/get-data/main-page")
    public ResponseEntity<?> getMainPageData(){
        try {
            return ResponseEntity.ok(dashboardUserService.getUserData());
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.internalServerError().body("Cannot fetch data");
        }
    }
    @PostMapping("/add-transaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
                Object response = new Object() {
                public final String status = userService.addTransaction(transactionRequestDTO);
            };
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
    @PostMapping("/add-recurring-expense")
        public ResponseEntity<?> addRecurringExpense(@RequestBody List<ExpenseModelDTO> expenseModelDTO){
        try {
            return ResponseEntity.ok(userService.addRecurringExpense(expenseModelDTO));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
    @PostMapping("/add-scheduled-expense")
    public ResponseEntity<?> addScheduledExpense(@RequestBody ExpenseModelDTO expenseModelDTO){
        try {
            return ResponseEntity.ok(userService.addScheduledExpenses(expenseModelDTO));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<?> getCategories() {
        try {
            return ResponseEntity.ok(transactionService.getAllCategories());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Cannot retrieve category names");
        }
    }

    @GetMapping("/check-for-scheduled-expenses")
    public ResponseEntity<?> getScheduledExpenses() {
        try {
            return ResponseEntity.ok(userService.checkForScheduledExpenses());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Cannot retrieve category names");
        }
    }
}
