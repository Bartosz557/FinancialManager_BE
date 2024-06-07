package com.example.FinancialManager.DTO.RequestBody;

import com.example.FinancialManager.DataModel.EnumTypes.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionRequestDTO {

    private String expenseName;
    private String categoryName;
    private double transactionValue;
    private TransactionType transactionType;
}