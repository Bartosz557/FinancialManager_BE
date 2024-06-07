package com.example.FinancialManager.DTO.Populators;

import com.example.FinancialManager.DataModel.AccountDetails;
import com.example.FinancialManager.DataModel.MonthlyHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@AllArgsConstructor
@Service
@Getter
public class MonthlyHistoryPopulator {

    public MonthlyHistory populateMonthlyHistory(AccountDetails accountDetails) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new MonthlyHistory(
                accountDetails.getUserDataAD(),
                currentDate.format(formatter),
                accountDetails.getExpenses(),
                accountDetails.getSavings(),
                accountDetails.getResidualFunds(),
                accountDetails.getAccountBalance()
        );
    }
}











