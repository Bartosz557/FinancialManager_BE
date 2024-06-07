package com.example.FinancialManager.WebServices;

import com.example.FinancialManager.DTO.ResponseModel.MainPageUserDataResponseDTO;
import com.example.FinancialManager.DAO.AccountDetailsRepository;
import com.example.FinancialManager.DAO.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardUserService {

    private final UserRepository userRepository;
    private final AccountDetailsRepository accountDetailsRepository;

    public MainPageUserDataResponseDTO getUserData(){
        MainPageUserDataResponseDTO mainPageUserDataResponseDTO = new MainPageUserDataResponseDTO();
        userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).ifPresent(foundUserData -> {
            mainPageUserDataResponseDTO.setUsername(foundUserData.getUsername());
            setUserData(foundUserData.getUserID(), mainPageUserDataResponseDTO);
        });
        return mainPageUserDataResponseDTO;
    }

    private void setUserData(Long userID, MainPageUserDataResponseDTO mainPageUserDataResponseDTO) {
        accountDetailsRepository.findById(userID).ifPresent(user -> {
            mainPageUserDataResponseDTO.setSettlementDate(user.getSettlementDate());
            mainPageUserDataResponseDTO.setLimit(user.getMonthlyLimit());
            mainPageUserDataResponseDTO.setAccountBalance(user.getAccountBalance());
            mainPageUserDataResponseDTO.setPiggyBank(user.getSavings());
            mainPageUserDataResponseDTO.setResidualFunds(user.getResidualFunds());
            mainPageUserDataResponseDTO.setExpenses(user.getExpenses());
        });
    }
}
