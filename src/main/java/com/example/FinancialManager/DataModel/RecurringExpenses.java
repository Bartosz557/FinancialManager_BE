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
public class RecurringExpenses {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long recurringExpenseId;
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userDataRE;
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private ExpenseCategories expenseCategoriesID;
    private String name;
    private int date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public RecurringExpenses(UserData userDataRE, String name, int date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.userDataRE = userDataRE;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }
}
