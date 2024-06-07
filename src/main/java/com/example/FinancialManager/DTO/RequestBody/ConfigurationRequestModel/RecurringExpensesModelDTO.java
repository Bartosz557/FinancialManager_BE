package com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecurringExpensesModelDTO {
    private boolean repeatingExpense;
    private List<ExpenseModelDTO> expense;
}
