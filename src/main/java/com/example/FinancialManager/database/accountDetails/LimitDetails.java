package com.example.FinancialManager.database.accountDetails;

import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.user.UserData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@IdClass(LimitDetailsId.class) // IDK how it exactly works but it works. Sets the multiple elements for PK with class' fields.
public class LimitDetails implements Serializable {

    @Id
    private Long user_id;

    @Id
    private Long category_id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserData userDataLD;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;

    private double limit_value;

    public LimitDetails(double limit_value) {
        this.limit_value = limit_value;
    }
}
