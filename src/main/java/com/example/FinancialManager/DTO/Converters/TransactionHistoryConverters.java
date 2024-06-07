package com.example.FinancialManager.DTO.Converters;

import com.example.FinancialManager.DTO.ResponseModel.MonthlyHistoryResponseDTO;
import com.example.FinancialManager.DTO.ResponseModel.TransactionHistoryResponseDTO;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionType;
import com.example.FinancialManager.DataModel.MonthlyHistory;
import com.example.FinancialManager.DataModel.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

@Setter
@AllArgsConstructor
@Service
@Getter
public class TransactionHistoryConverters {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryConverters.class);
    public TransactionHistoryResponseDTO convertToTransactionObject(TransactionHistory record) {
        TransactionHistoryResponseDTO transactionHistoryResponseDTO = new TransactionHistoryResponseDTO();
        transactionHistoryResponseDTO.setDate(convertDateFormat(record.getDate()));
        transactionHistoryResponseDTO.setTransactionType(convertTransactionType(record.getTransactionType()));
        if(record.getTransactionType()==TransactionType.EXPENSE)
            transactionHistoryResponseDTO.setAmount(record.getTransactionValue()*(-1));
        else
            transactionHistoryResponseDTO.setAmount(record.getTransactionValue());
        transactionHistoryResponseDTO.setCategory(record.getExpenseCategories().getCategoryName());
        transactionHistoryResponseDTO.setName(record.getTransactionName());
        return transactionHistoryResponseDTO;
    }

    private String convertTransactionType(TransactionType transactionType) {
        if (transactionType == TransactionType.EXPENSE)
            return "Expense";
        else if (transactionType == TransactionType.DEPOSIT)
            return "Deposit";
        else
            throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
    }

    public String convertDateFormat(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(inputDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            logger.error("Failed to parse date: {}", inputDate, e);
            return null;
        }
    }

    public MonthlyHistoryResponseDTO convertToMonthlyObject(MonthlyHistory record) {
        MonthlyHistoryResponseDTO monthlyHistoryResponseDTO = new MonthlyHistoryResponseDTO();
        monthlyHistoryResponseDTO.setBalance(record.getArchivalBalance());
        monthlyHistoryResponseDTO.setSavings(record.getArchivalSavings());
        monthlyHistoryResponseDTO.setResidualFunds(record.getArchivalResidualFunds());
        monthlyHistoryResponseDTO.setAmount(record.getArchivalExpense());
        monthlyHistoryResponseDTO.setDate(getMonth(record.getData()));
        return monthlyHistoryResponseDTO;
    }

    private String getMonth(String inputDate) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            DateTimeFormatter outputDate = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
            logger.info(date.format(outputDate));
            return date.format(outputDate);
        } catch (DateTimeParseException e) {
            logger.error("Failed to parse date: {}", inputDate, e);
            return null;
        }

    }
}
