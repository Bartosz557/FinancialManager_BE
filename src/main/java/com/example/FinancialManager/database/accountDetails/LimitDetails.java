package com.example.FinancialManager.database.accountDetails;

import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.user.UserData;
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
public class LimitDetails implements Serializable {

    @EmbeddedId
    private LimitDetailsId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private UserData userDataLD;

    @ManyToOne
    @MapsId("categoryID")
    @JoinColumn(name = "categoryID")
    private ExpenseCategories expenseCategoriesID;

    private double limitValue;

    public LimitDetails(double limitValue) {
        this.limitValue = limitValue;
    }

    @Override
    public String toString() {
        return "LimitDetails{" +
                "id=" + id +
                ", userDataLD=" + userDataLD +
                ", expenseCategoriesID=" + expenseCategoriesID +
                ", limitValue=" + limitValue +
                '}';
    }
}
