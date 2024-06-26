package com.example.FinancialManager.API;

import com.example.FinancialManager.WebServices.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/wallet-history")
public class TransactionHistoryController {

    TransactionHistoryService transactionHistoryService;
    @GetMapping("/transaction/get-data")
    public ResponseEntity<?> getTransactionHistoryData(){
        try {
            return ResponseEntity.ok(transactionHistoryService.getTransactionsList());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @GetMapping("/monthly/get-data")
    public ResponseEntity<?> getMonthlyHistoryData(){
        try {
            return ResponseEntity.ok(transactionHistoryService.getMonthlyList());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }
}
