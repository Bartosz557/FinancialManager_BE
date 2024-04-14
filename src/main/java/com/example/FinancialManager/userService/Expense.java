package com.example.FinancialManager.userService;

import javassist.compiler.ast.StringL;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense {
    private String name;
    private String date;
    private int amount;
}
