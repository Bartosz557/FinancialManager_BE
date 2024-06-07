package com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainConfigModelDTO {
    private int settlementDate;
    private int monthlyIncome;
    private int monthlyLimit;
    private double accountBalance;

}
