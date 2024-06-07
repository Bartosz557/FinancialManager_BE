package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MonthlyHistoryResponseDTO {
    private String date;
    private double amount;
    private double balance;
    private double savings;
    private double residualFunds;
}
