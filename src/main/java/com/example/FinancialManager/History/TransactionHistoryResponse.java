package com.example.FinancialManager.History;

import com.example.FinancialManager.database.Repositories.TransactionHistoryRepository;
import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.userHistory.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionHistoryResponse {
    private String date;
    private String transactionType;
    private double amount;
    private String category;
    private String name;

}
