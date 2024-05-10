package com.example.FinancialManager.History;

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
}
