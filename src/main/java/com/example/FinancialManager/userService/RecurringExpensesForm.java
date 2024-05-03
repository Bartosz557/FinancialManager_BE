package com.example.FinancialManager.userService;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecurringExpensesForm {
    private boolean repeatingExpense;
    private List<Expense> expenses;
}