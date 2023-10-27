package com.example.FinancialManager.user.transactions;

import com.example.FinancialManager.user.appUser.AppUser;
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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long scheduled_expense_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    @Enumerated(EnumType.STRING)
    private  TransactionStatus transactionStatus;

    public ScheduledExpenses(AppUser appUser, Date date, double amount, ReminderType reminderType, TransactionStatus transactionStatus) {
        this.appUser = appUser;
        this.date = date;
        this.amount = amount;
        this.reminderType = reminderType;
        this.transactionStatus = transactionStatus;
    }
}
