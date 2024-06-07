package com.example.FinancialManager.Security.Validators;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return true;
    }
}