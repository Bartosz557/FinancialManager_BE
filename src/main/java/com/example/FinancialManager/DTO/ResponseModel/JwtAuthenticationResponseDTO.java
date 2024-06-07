package com.example.FinancialManager.DTO.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthenticationResponseDTO {
    private String accessToken;
}