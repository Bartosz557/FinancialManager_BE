package com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel;

import com.example.FinancialManager.DataModel.EnumTypes.ReminderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseModelDTO {
    private String name;
    private String date;
    private double amount;
    private ReminderType reminderType;
    private String category;
}
