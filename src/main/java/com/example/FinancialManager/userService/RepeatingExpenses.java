package com.example.FinancialManager.userService;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepeatingExpenses {
    private boolean repeatingExpense;
    List<Expense> expenses;
}
