package com.example.FinancialManager.database.accountDetails;

import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.user.UserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LimitDetailsId implements Serializable {

    private Long userID;
    private Long categoryID;
}
