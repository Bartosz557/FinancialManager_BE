package com.example.FinancialManager.database.transactions;

import com.example.FinancialManager.database.accountDetails.LimitDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ExpenseCategories {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long categoryId;
    private String categoryName;
    @OneToMany(mappedBy = "expenseCategories")
    private List<LimitDetails> limitDetails;

    public ExpenseCategories(String category_name) {
        this.categoryName = category_name;
    }
}
