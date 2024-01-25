package com.example.FinancialManager.userService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsForm {
    private String username;
    private String email;
    private boolean configured;
    private boolean enabled;
}
