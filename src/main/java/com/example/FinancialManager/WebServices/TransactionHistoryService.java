package com.example.FinancialManager.WebServices;


import com.example.FinancialManager.DTO.ResponseModel.MonthlyHistoryResponseDTO;
import com.example.FinancialManager.DTO.ResponseModel.TransactionHistoryResponseDTO;
import com.example.FinancialManager.DTO.Converters.TransactionHistoryConverters;
import com.example.FinancialManager.DAO.MonthlyHistoryRepository;
import com.example.FinancialManager.DAO.TransactionHistoryRepository;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.MonthlyHistory;
import com.example.FinancialManager.DataModel.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TransactionHistoryService {
    TransactionHistoryRepository transactionHistoryRepository;
    TransactionHistoryConverters transactionHistoryConverters;
    MonthlyHistoryRepository monthlyHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryService.class);
    public List<TransactionHistoryResponseDTO> getTransactionsList(){
        List<TransactionHistory> transactionHistoryList;
        List<TransactionHistoryResponseDTO> transactionResponseList = new ArrayList<>();
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionHistoryList = transactionHistoryRepository.findAllByUserData(userData).orElseThrow(() -> new RuntimeException("Transaction history not found"));
        for(TransactionHistory record : transactionHistoryList){
            transactionResponseList.add(transactionHistoryConverters.convertToTransactionObject(record));

        }
        return transactionResponseList;
    }


    public List<MonthlyHistoryResponseDTO> getMonthlyList() {
        List<MonthlyHistory> monthlyHistoryList;
        List<MonthlyHistoryResponseDTO> monthlyHistoryResponsDTOS = new ArrayList<>();
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        monthlyHistoryList = monthlyHistoryRepository.findAllByUserData(userData).orElseThrow(() -> new RuntimeException("Monthly history not found"));
        for(MonthlyHistory record : monthlyHistoryList){
            monthlyHistoryResponsDTOS.add(transactionHistoryConverters.convertToMonthlyObject(record));

        }
        return monthlyHistoryResponsDTOS;
    }
}
