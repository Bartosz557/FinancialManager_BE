package com.example.FinancialManager.DTO.ResponseModel;

import com.example.FinancialManager.DataModel.EnumTypes.ReminderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpcomingPaymentsResponseDTO {
    private String date;
    private double amount;
    private String name;
    private ReminderType reminder;
}
