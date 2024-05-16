package com.example.FinancialManager.database.transactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseReminderResponse {
    private List<ExpenseReminderData> today;
    private List<ExpenseReminderData> tomorrow;
    private List<ExpenseReminderData> nextWeek;
}
