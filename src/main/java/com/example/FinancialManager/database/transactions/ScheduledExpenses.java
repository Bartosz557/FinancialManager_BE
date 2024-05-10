package com.example.FinancialManager.database.transactions;

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
public class ScheduledExpenses {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long scheduled_expense_id;
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userDataSE;
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private ExpenseCategories expenseCategoriesID;
    private String name;
    private String date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    @Enumerated(EnumType.STRING)
    private  TransactionStatus transactionStatus;

    public ScheduledExpenses(UserData userDataSE, String date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.userDataSE = userDataSE;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }

    public ScheduledExpenses(String date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }
}
