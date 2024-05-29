package com.example.FinancialManager.database.transactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpcomingPaymentsDTO {
    private String date;
    private double amount;
    private String name;
    private ReminderType reminder;
}
