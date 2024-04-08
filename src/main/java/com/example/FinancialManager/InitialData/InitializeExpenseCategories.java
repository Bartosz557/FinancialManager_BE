package com.example.FinancialManager.InitialData;

import com.example.FinancialManager.database.Repositories.ExpenseCategoriesRepository;
import com.example.FinancialManager.database.transactions.ExpenseCategories;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class InitializeExpenseCategories implements CommandLineRunner {

    ExpenseCategoriesRepository expenseCategoriesRepository;
    private static final String[] CATEGORIES = {
                "groceries",
                "diningOut",
                "transport",
                "entertainment",
                "clothes",
                "traveling",
                "hobby",
                "miscellaneous"
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
