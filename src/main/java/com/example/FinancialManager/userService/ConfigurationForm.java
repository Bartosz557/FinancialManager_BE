package com.example.FinancialManager.userService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationForm {

    private MainConfig mainConfig;
    private SubCategories subCategories;
    private RecurringExpensesForm repeatingExpenses;
}
