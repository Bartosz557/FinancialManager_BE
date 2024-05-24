package com.example.FinancialManager.database.transactions;

import com.example.FinancialManager.database.Repositories.ExpenseCategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    ExpenseCategoriesRepository expenseCategoriesRepository;
    public List<String> getAllCategories() {
        List<String>  categoryNames = new ArrayList<>();
        String name;
        List<ExpenseCategories> categories= expenseCategoriesRepository.findAll();
        for( ExpenseCategories category : categories){
            name=category.getCategoryName();
            if(!name.equals("recurringExpense") && !name.equals("piggyBank")) {
                if(name.equals("DiningOut"))
                    name="Dining out";
                categoryNames.add(name);
            }
        }
        Collections.sort(categoryNames);
        return categoryNames;
    }
}
