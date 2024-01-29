package com.example.FinancialManager.database.userHistory;

import com.example.FinancialManager.database.user.UserData;
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
public class MonthlyHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long monthId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;
    private int monthNumber;
    private String archival_expense;
    private String archival_savings;
    private String archival_residual_funds;
    private String archival_balance;

    public MonthlyHistory(UserData userData, int monthNumber, String archival_expense, String archival_savings, String archival_residual_funds, String archival_balance) {
        this.userData = userData;
        this.monthNumber = monthNumber;
        this.archival_expense = archival_expense;
        this.archival_savings = archival_savings;
        this.archival_residual_funds = archival_residual_funds;
        this.archival_balance = archival_balance;
    }
}
