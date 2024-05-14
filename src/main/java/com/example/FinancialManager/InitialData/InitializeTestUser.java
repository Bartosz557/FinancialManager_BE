package com.example.FinancialManager.InitialData;

import com.example.FinancialManager.database.Repositories.AccountDetailsRepository;
import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.example.FinancialManager.userService.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class InitializeTestUser implements CommandLineRunner{

    private final UserService userService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final AccountDetailsRepository accountDetailsRepository;
    @Override
    @Transactional // Fixing the detached persistence in the second operation UserData
    public void run(String... args) {
        initializeUser();
    }
    public void initializeUser() {
        if (userRepository.findByUsername("TestUser").isEmpty()) {
            UserData userData = new UserData("TestUser", "user@mail.com", "123", UserRole.USER, true, true);
            userService.signUpUser(userData);
            AccountDetails accountDetails = new AccountDetails(userData,0,11,1234.0,1000,2000,1400.0,50,30.0);
            try{
                accountDetailsRepository.save(accountDetails);
            } catch (Exception e){
                logger.error("Failed to save account details", e);
            }
        }
    }
}
