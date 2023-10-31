package com.example.FinancialManager.registration;

import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.example.FinancialManager.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException(request.getEmail() + " is not valid");
        }
        return userService.signUpUser(
                new UserData(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                ));
    }
}
