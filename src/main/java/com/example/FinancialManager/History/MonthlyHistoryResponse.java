package com.example.FinancialManager.History;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MonthlyHistoryResponse {
    private String date;
    private double amount;
    private double balance;
    private double savings;
    private double residualFunds;
}
