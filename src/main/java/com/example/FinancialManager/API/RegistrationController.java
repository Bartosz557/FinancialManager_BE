package com.example.FinancialManager.API;

import com.example.FinancialManager.DTO.RequestBody.RegistrationRequestDTO;
import com.example.FinancialManager.WebServices.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping
    public String register(@RequestBody RegistrationRequestDTO request)
    {
        return registrationService.register(request);
    }

}
