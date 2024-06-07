package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsResponseDTO {
    private String username;
    private String email;
    private boolean configured;
    private boolean enabled;
}
