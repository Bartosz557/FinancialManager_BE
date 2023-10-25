package com.example.FinancialManager.registration;

import com.example.FinancialManager.user.appUser.AppUser;
import com.example.FinancialManager.user.appUser.AppUserRole;
import com.example.FinancialManager.user.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail)
        {
            throw new IllegalStateException(request.getEmail() + " is not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                ));
    }
}
