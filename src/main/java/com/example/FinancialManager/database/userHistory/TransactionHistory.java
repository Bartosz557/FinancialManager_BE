package com.example.FinancialManager.database.userHistory;

import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.transactions.TransactionType;
import com.example.FinancialManager.database.user.UserData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long transaction_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;
    private Date date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String transaction_detail;

    public TransactionHistory(UserData userData, ExpenseCategories expenseCategories, Date date, TransactionType transactionType, String transaction_detail) {
        this.userData = userData;
        this.expenseCategories = expenseCategories;
        this.date = date;
        this.transactionType = transactionType;
        this.transaction_detail = transaction_detail;
    }
}
