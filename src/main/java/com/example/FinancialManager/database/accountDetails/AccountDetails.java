package com.example.FinancialManager.database.accountDetails;

import com.example.FinancialManager.database.user.UserData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AccountDetails implements Serializable {

    @Id
    private Long accountDetailsID;
    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private UserData userDataAD;
    private String settlementDate;
    private double accountBalance;
    private int monthlyIncome;
    private int monthlyLimit;
    private double expenses; // expenses for current month
    private double savings; // piggy bank
    private double residualFunds; // funds left from previous months

    public AccountDetails(String settlementDate, double accountBalance, int monthlyIncome, int monthlyLimit, double expenses, double savings, double residualFunds) {
        this.settlementDate = settlementDate;
        this.accountBalance = accountBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyLimit = monthlyLimit;
        this.expenses = expenses;
        this.savings = savings;
        this.residualFunds = residualFunds;
    }

}
