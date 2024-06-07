package com.example.FinancialManager.InitialData;

import com.example.FinancialManager.DAO.UserRepository;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.WebServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class InitializeAdminUser implements CommandLineRunner {

    public InitializeAdminUser(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    private final UserService userService;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) {
        initializeAdmin();
    }
    private void initializeAdmin() {
        String adminUsername = "admin";
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            String adminPassword = "nimda";
            userService.signUpUser(
                    new UserData(
                            adminUsername,
                            "admin@mail.com",
                            adminPassword,
                            UserRole.ADMIN
                    ));
        }
    }
}
