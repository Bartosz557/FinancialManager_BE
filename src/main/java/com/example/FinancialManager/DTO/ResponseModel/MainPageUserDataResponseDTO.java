package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MainPageUserDataResponseDTO {
    private String username;
    private int settlementDate;
    private int limit;
    private double accountBalance;
    private double piggyBank;
    private double residualFunds;
    private double expenses;
}
