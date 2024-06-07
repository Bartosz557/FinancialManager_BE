package com.example.FinancialManager.InitialData;

import com.example.FinancialManager.DAO.AccountDetailsRepository;
import com.example.FinancialManager.DAO.UserRepository;
import com.example.FinancialManager.DataModel.AccountDetails;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.WebServices.UserService;
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
    @Transactional // Fixing the detached persistence in the second operation
    public void run(String... args) {
        initializeUser();
    }
    public void initializeUser() {
        if (userRepository.findByUsername("TestUser").isEmpty()) {
            UserData userData = new UserData("Bartosz", "bartosz@gmail.com", "123", UserRole.USER, true, true);
            userService.signUpUser(userData);
            AccountDetails accountDetails = new AccountDetails(userData,100,18,4865.75,1000,2300,1600,310,91);
            try{
                accountDetailsRepository.save(accountDetails);
            } catch (Exception e){
                logger.error("Failed to save account details for initial user", e);
            }
        }
    }
}
