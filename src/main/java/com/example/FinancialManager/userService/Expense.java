package com.example.FinancialManager.userService;

import com.example.FinancialManager.database.transactions.ReminderType;
import javassist.compiler.ast.StringL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
    private String name;
    private int date;
    private int amount;
    private ReminderType reminderType;
}
