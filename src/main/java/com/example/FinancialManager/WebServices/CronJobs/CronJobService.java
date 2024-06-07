package com.example.FinancialManager.WebServices.CronJobs;

import com.example.FinancialManager.DAO.*;
import com.example.FinancialManager.DTO.Populators.MonthlyHistoryPopulator;
import com.example.FinancialManager.DTO.Converters.TransactionHistoryConverters;
import com.example.FinancialManager.DataModel.AccountDetails;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.DataModel.MonthlyHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Service
public class CronJobService {

    private final UserRepository userRepository;
    private final MonthlyHistoryRepository monthlyHistoryRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final TransactionHistoryConverters transactionHistoryConverters;
    private final RecurringExpensesRepository recurringExpensesRepository;
    private final ScheduledExpensesRepository scheduledExpensesRepository;
    private final MonthlyHistoryPopulator monthlyHistoryPopulator;
    public void monthlyResetJob(){
        List<UserData> userList = userRepository.findAllByUserRole(UserRole.USER);
        for( UserData user: userList){
            AccountDetails accountDetails = accountDetailsRepository.findByUserDataAD(user).orElseThrow(() -> new RuntimeException("User account details not found"));
            if(accountDetails.getSettlementDate()!=LocalDate.now().getDayOfMonth())
                continue;
            if (addMonthlyHistory(accountDetails))
                userSettlement(accountDetails);
        }

    }

    // TODO: create update methods for this cringe code below
    private void userSettlement(AccountDetails accountDetails) {
        accountDetails.setAccountBalance(accountDetails.getAccountBalance()+accountDetails.getMonthlyIncome()-accountDetails.getSavings());
        accountDetails.setExpenses(0.0);
        accountDetails.setResidualFunds(accountDetails.getResidualFunds()+(accountDetails.getMonthlyLimit()-accountDetails.getExpenses()));
        accountDetails.setSavings(accountDetails.getSavings()+accountDetails.getMonthlySavings());
        accountDetailsRepository.save(accountDetails);
    }

    private boolean addMonthlyHistory(AccountDetails accountDetails) {
        try {
            MonthlyHistory monthlyHistory = monthlyHistoryPopulator.populateMonthlyHistory(accountDetails);
            monthlyHistoryRepository.save(monthlyHistory);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
