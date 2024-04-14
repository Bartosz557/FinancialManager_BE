package com.example.FinancialManager.ProfileDashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MainPageUserData {
    private String username;
    private int settlementDate;
    private int limit;
    private double accountBalance;
    private double piggyBank;
    private double residualFunds;
    private double expenses;
}
