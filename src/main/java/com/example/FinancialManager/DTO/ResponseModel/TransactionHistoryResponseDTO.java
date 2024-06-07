package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionHistoryResponseDTO {
    private String date;
    private String transactionType;
    private double amount;
    private String category;
    private String name;

}
