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
        List<ExpenseCategories> categories= expenseCategoriesRepository.findAll();
        for( ExpenseCategories category : categories){
            categoryNames.add(category.getCategoryName());
        }
        Collections.sort(categoryNames);
        return categoryNames;
    }
}
