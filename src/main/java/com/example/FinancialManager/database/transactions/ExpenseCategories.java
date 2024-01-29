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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long category_id;
    private String category_name;
    @OneToMany(mappedBy = "expenseCategories")
    private List<LimitDetails> limitDetails;

    public ExpenseCategories(String category_name) {
        this.category_name = category_name;
    }
}
