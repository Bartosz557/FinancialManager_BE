package com.example.FinancialManager.user.userHistory;

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
public class MonthlyHistory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long month_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private int month_number;
    private String archival_expense;
    private String archival_savings;
    private String archival_residual_funds;
    private String archival_balance;

    public MonthlyHistory(AppUser appUser, int month_number, String archival_expense, String archival_savings, String archival_residual_funds, String archival_balance) {
        this.appUser = appUser;
        this.month_number = month_number;
        this.archival_expense = archival_expense;
        this.archival_savings = archival_savings;
        this.archival_residual_funds = archival_residual_funds;
        this.archival_balance = archival_balance;
    }
}
