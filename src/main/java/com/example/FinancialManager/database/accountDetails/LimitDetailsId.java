package com.example.FinancialManager.database.accountDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LimitDetailsId implements Serializable {

    private Long user_id;
    private Long category_id;
}
