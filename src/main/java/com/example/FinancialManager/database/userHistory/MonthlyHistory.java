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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long monthId;
    @ManyToOne
    @JoinColumn(name = "userID")
    private UserData userData;
    private String data;
    private double archivalExpense;
    private double archivalSavings;
    private double archivalResidualFunds;
    private double archivalBalance;

    public MonthlyHistory(UserData userData, String data, double archival_expense, double archival_savings, double archival_residual_funds, double archival_balance) {
        this.userData = userData;
        this.data = data;
        this.archivalExpense = archival_expense;
        this.archivalSavings = archival_savings;
        this.archivalResidualFunds = archival_residual_funds;
        this.archivalBalance = archival_balance;
    }
}
