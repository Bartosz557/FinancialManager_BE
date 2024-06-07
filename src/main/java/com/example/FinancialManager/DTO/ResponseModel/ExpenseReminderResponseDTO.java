package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseReminderResponseDTO {
    private List<ExpenseReminderDataResponseDTO> today;
    private List<ExpenseReminderDataResponseDTO> tomorrow;
    private List<ExpenseReminderDataResponseDTO> nextWeek;
}
