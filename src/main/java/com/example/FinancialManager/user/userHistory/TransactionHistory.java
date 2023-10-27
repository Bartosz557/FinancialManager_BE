package com.example.FinancialManager.user.userHistory;

import com.example.FinancialManager.user.appUser.AppUser;
import com.example.FinancialManager.user.transactions.ExpenseCategories;
import com.example.FinancialManager.user.transactions.TransactionType;
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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long transaction_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;
    private Date date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String transaction_detail;

    public TransactionHistory(AppUser appUser, ExpenseCategories expenseCategories, Date date, TransactionType transactionType, String transaction_detail) {
        this.appUser = appUser;
        this.expenseCategories = expenseCategories;
        this.date = date;
        this.transactionType = transactionType;
        this.transaction_detail = transaction_detail;
    }
}
