package com.example.FinancialManager.CronJobs;

import com.example.FinancialManager.History.TransactionHistoryConverters;
import com.example.FinancialManager.database.Repositories.AccountDetailsRepository;
import com.example.FinancialManager.database.Repositories.MonthlyHistoryRepository;
import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.example.FinancialManager.database.userHistory.MonthlyHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Setter
@Getter
@Service
public class CronJobService {

    private final UserRepository userRepository;
    private final MonthlyHistoryRepository monthlyHistoryRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final TransactionHistoryConverters transactionHistoryConverters;
    public void monthlyResetJob(){
        List<UserData> userList = userRepository.findAll();
        for( UserData user: userList){
            if(user.getUserRole() == UserRole.USER){
                AccountDetails accountDetails = accountDetailsRepository.findByUserDataAD(user).orElseThrow(() -> new RuntimeException("User account details not found"));
                if(accountDetails.getSettlementDate()!=LocalDate.now().getDayOfMonth())
                    continue;
                if (addMonthlyHistory(accountDetails))
                    userSettlement(accountDetails);
            }
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
            MonthlyHistory monthlyHistory = transactionHistoryConverters.populateMonthlyHistory(accountDetails);
            monthlyHistoryRepository.save(monthlyHistory);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
