package com.example.FinancialManager.ProfileDashboard;

import com.example.FinancialManager.database.transactions.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionForm {
    private String expenseName;
    private String categoryName;
    private double transactionValue;
    private TransactionType transactionType;
}
