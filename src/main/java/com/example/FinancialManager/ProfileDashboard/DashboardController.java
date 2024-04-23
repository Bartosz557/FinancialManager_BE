package com.example.FinancialManager.ProfileDashboard;

import com.example.FinancialManager.database.transactions.ScheduledExpenses;
import com.example.FinancialManager.userService.Expense;
import com.example.FinancialManager.userService.UserService;
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
    @PostMapping("/add-recurring-expense")
        public ResponseEntity<?> addRecurringExpense(@RequestBody List<Expense> expense){
        try {
            return ResponseEntity.ok(userService.addRecurringExpense(expense));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
    @PostMapping("/add-scheduled-expense")
    public ResponseEntity<?> addScheduledExpense(@RequestBody ScheduledExpenses scheduledExpenses){
        try {
            return ResponseEntity.ok(userService.addScheduledExpenses(scheduledExpenses));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
