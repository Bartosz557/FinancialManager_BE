package com.example.FinancialManager.DTO.RequestBody;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequestDTO {

    private final String username;
    private final String email;
    private final String password;
}
