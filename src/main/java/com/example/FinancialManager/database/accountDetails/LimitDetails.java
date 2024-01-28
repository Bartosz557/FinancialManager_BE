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
    private Long userID;

    @Id
    private Long categoryID;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "userID")
    private UserData userDataLD;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "categoryID")
    private ExpenseCategories expenseCategories;

    private double limitValue;

    public LimitDetails(double limitValue) {
        this.limitValue = limitValue;
    }
}
