package com.example.FinancialManager.user.accountDetails;

import com.example.FinancialManager.user.appUser.AppUser;
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
    private Long account_details_id;  //mapped

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private Date settlement_date;
    private double account_balance;
    private int monthly_limit;
    private double savings;
    private double residual_funds;

    public AccountDetails(Date settlement_date, double account_balance, int monthly_limit, double savings, double residual_funds) {
        this.settlement_date = settlement_date;
        this.account_balance = account_balance;
        this.monthly_limit = monthly_limit;
        this.savings = savings;
        this.residual_funds = residual_funds;
    }

}
