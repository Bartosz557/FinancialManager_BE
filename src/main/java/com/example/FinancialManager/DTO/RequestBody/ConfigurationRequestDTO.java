package com.example.FinancialManager.DTO.RequestBody;

import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.MainConfigModelDTO;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.RecurringExpensesModelDTO;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.SubCategoriesModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationRequestDTO {

    private MainConfigModelDTO mainConfig;
    private SubCategoriesModelDTO subCategories;
    private RecurringExpensesModelDTO repeatingExpenses;
}
