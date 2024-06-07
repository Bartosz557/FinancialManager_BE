package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseReminderDataResponseDTO {
    private String expenseName;
    private String category;
    private double amount;
}
