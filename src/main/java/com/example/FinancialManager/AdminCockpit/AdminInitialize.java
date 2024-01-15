package com.example.FinancialManager.AdminCockpit;

import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.example.FinancialManager.userService.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class AdminInitialize implements CommandLineRunner {

    public AdminInitialize(UserService userService, UserRepository userRepository) {
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
