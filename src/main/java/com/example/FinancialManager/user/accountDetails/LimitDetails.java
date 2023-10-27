package com.example.FinancialManager.user.accountDetails;

import com.example.FinancialManager.user.appUser.AppUser;
import com.example.FinancialManager.user.transactions.ExpenseCategories;
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
@IdClass(LimitDetailsId.class)
public class LimitDetails implements Serializable {

    @Id
    private Long user_id;

    @Id
    private Long category_id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "category_id")
    private ExpenseCategories expenseCategories;

    private double limit_value;

    public LimitDetails(double limit_value) {
        this.limit_value = limit_value;
    }
}
