package com.example.FinancialManager.InitialData;

import com.example.FinancialManager.DAO.ExpenseCategoriesRepository;
import com.example.FinancialManager.DataModel.ExpenseCategories;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitializeExpenseCategories implements CommandLineRunner {

    ExpenseCategoriesRepository expenseCategoriesRepository;
    private static final String[] CATEGORIES = {
                "piggyBank", // TODO: ignore this index everywhere
                "recurringExpense", //TODO: This too
                "groceries",
                "diningOut",
                "transport",
                "entertainment",
                "clothes",
                "traveling",
                "hobby",
                "miscellaneous",
                "other",

    };
    @Override
    public void run(String... args) {
        initializeCategories();
    }

    private void initializeCategories() {
        if(expenseCategoriesRepository.count()==0) {
            for (String category : CATEGORIES) {
                expenseCategoriesRepository.save(new ExpenseCategories(category));
            }
        }
    }
}
