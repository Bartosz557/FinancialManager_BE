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
    private Date settlementDate;
    private double accountBalance;
    private int monthlyLimit;
    private double savings;
    private double residualFunds;

    public AccountDetails(Date settlementDate, double accountBalance, int monthlyLimit, double savings, double residualFunds) {
        this.settlementDate = settlementDate;
        this.accountBalance = accountBalance;
        this.monthlyLimit = monthlyLimit;
        this.savings = savings;
        this.residualFunds = residualFunds;
    }

}
