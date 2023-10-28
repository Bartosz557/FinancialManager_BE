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
public class RecurringExpenses {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long recurring_expense_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;
    private Date date; // FIXME  probably its gonna be string not date
    private double amount;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    @Enumerated(EnumType.STRING)
    private  TransactionStatus transactionStatus;

    public RecurringExpenses(UserData userData, Date date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.userData = userData;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }
}
