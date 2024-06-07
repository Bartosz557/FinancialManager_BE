package com.example.FinancialManager.DataModel;

import com.example.FinancialManager.DataModel.ExpenseCategories;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionType;
import com.example.FinancialManager.DataModel.UserData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long transactionId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;
    private String date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String transactionName;
    private double transactionValue;

    public TransactionHistory(UserData userData, ExpenseCategories expenseCategories, String date, TransactionType transactionType, String transactionName, double transactionValue) {
        this.userData = userData;
        this.expenseCategories = expenseCategories;
        this.date = date;
        this.transactionType = transactionType;
        this.transactionName = transactionName;
        this.transactionValue = transactionValue;
    }
}
