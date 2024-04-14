package com.example.FinancialManager.ProfileDashboard;

import com.example.FinancialManager.database.Repositories.AccountDetailsRepository;
import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.user.UserData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardUserService {

    private final UserRepository userRepository;
    private final AccountDetailsRepository accountDetailsRepository;

    public MainPageUserData getUserData(){
        MainPageUserData mainPageUserData = new MainPageUserData();
        userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).ifPresent(foundUserData -> {
            mainPageUserData.setUsername(foundUserData.getUsername());
            setUserData(foundUserData.getUserID(),mainPageUserData);
        });
        return mainPageUserData;
    }

    private void setUserData(Long userID, MainPageUserData mainPageUserData) {
        accountDetailsRepository.findById(userID).ifPresent(user -> {
            mainPageUserData.setSettlementDate(user.getSettlementDate());
            mainPageUserData.setLimit(user.getMonthlyLimit());
            mainPageUserData.setAccountBalance(user.getAccountBalance());
            mainPageUserData.setPiggyBank(user.getSavings());
            mainPageUserData.setResidualFunds(user.getResidualFunds());
            mainPageUserData.setExpenses(user.getExpenses());
        });
    }
}
