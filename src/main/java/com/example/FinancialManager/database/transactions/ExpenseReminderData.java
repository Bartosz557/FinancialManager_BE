package com.example.FinancialManager.database.transactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseReminderData {
    private String expenseName;
    private String category;
    private double amount;
}
