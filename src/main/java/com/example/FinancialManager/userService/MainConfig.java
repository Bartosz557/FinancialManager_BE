package com.example.FinancialManager.userService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainConfig {
    private String settlementDate;
    private int monthlyIncome;
    private int monthlyLimit;
    private double accountBalance;

}
