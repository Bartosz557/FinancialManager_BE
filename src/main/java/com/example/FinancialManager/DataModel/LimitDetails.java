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
public class LimitDetails implements Serializable {

    @EmbeddedId     // Creates the embedded id of LimitDetailsID type,
                    // which contains unique pair of userID and categoryID
    private LimitDetailsId id;

    @ManyToOne                   //Define relation between tables
    @MapsId("userID")            //Maps userID into accountDetailsID
    @JoinColumn(name = "userID") // Maps Foreign Key value
    private UserData userDataLD;

    @ManyToOne
    @MapsId("categoryID")
    @JoinColumn(name = "categoryID")
    private ExpenseCategories expenseCategoriesID;

    private double limitValue;

    public LimitDetails(double limitValue) {
        this.limitValue = limitValue;
    }
}
