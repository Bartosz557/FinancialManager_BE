package com.example.FinancialManager.DataModel;

import com.example.FinancialManager.DataModel.EnumTypes.ReminderType;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionStatus;
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
public class ScheduledExpenses{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long scheduledExpenseId;
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
    private TransactionStatus transactionStatus;

    public ScheduledExpenses(UserData userDataSE, String date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.userDataSE = userDataSE;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }

    public ScheduledExpenses(String name, String date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }
}
