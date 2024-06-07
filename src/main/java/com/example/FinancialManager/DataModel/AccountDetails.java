package com.example.FinancialManager.DataModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AccountDetails implements Serializable {

    @Id
    private Long accountDetailsID; // Primary Key
    @OneToOne                      // Relation
    @MapsId                        // Maps userID into accountDetailsID
    @JoinColumn(name = "userID")   // Maps Foreign Key value
    private UserData userDataAD;   // Foreign Key
    private int settlementDate;
    private double accountBalance;
    private int monthlyIncome;
    private int monthlyLimit;
    private double monthlySavings;
    private double expenses;
    private double savings;
    private double residualFunds;

    public AccountDetails(UserData userDataAD, int monthlySavings , int settlementDate, double accountBalance, int monthlyIncome, int monthlyLimit, double expenses, int savings, double residualFunds) {
        this.userDataAD = userDataAD;
        this.settlementDate = settlementDate;
        this.monthlySavings = monthlySavings;
        this.accountBalance = accountBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyLimit = monthlyLimit;
        this.expenses = expenses;
        this.savings = savings;
        this.residualFunds = residualFunds;
    }
}
