package com.example.FinancialManager.DTO.RequestBody;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequestDTO {
    private String email;
    private String password;
}