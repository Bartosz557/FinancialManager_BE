package com.example.FinancialManager.WebServices;

import com.example.FinancialManager.DTO.RequestBody.RegistrationRequestDTO;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.Security.Validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final LogService logService;
    public String register(RegistrationRequestDTO request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException(request.getEmail() + " is not valid");
        }
        logService.addRegistrationLog(request.getEmail());
        return userService.signUpUser(
                new UserData(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                ));
    }
}




