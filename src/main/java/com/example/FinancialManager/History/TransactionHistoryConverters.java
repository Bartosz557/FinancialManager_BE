package com.example.FinancialManager.History;

import com.example.FinancialManager.AdminCockpit.AdminController;
import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.transactions.TransactionType;
import com.example.FinancialManager.database.userHistory.MonthlyHistory;
import com.example.FinancialManager.database.userHistory.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    public TransactionHistoryResponse convertToTransactionObject(TransactionHistory record) {
        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
        transactionHistoryResponse.setDate(convertDateFormat(record.getDate()));
        transactionHistoryResponse.setTransactionType(convertTransactionType(record.getTransactionType()));
        if(record.getTransactionType()==TransactionType.EXPENSE)
            transactionHistoryResponse.setAmount(record.getTransactionValue()*(-1));
        else
            transactionHistoryResponse.setAmount(record.getTransactionValue());
        transactionHistoryResponse.setCategory(record.getExpenseCategories().getCategoryName());
        transactionHistoryResponse.setName(record.getTransactionName());
        return transactionHistoryResponse;
    }


    private String convertTransactionType(TransactionType transactionType) {
        if (transactionType == TransactionType.EXPENSE)
            return "Expense";
        else if (transactionType == TransactionType.DEPOSIT)
            return "Deposit";
        else
            throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
    }

    private String convertDateFormat(String inputDate) {
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

    public MonthlyHistoryResponse convertToMonthlyObject(MonthlyHistory record) {
        MonthlyHistoryResponse monthlyHistoryResponse = new MonthlyHistoryResponse();
        monthlyHistoryResponse.setBalance(record.getArchivalBalance());
        monthlyHistoryResponse.setSavings(record.getArchivalSavings());
        monthlyHistoryResponse.setResidualFunds(record.getArchivalResidualFunds());
        monthlyHistoryResponse.setAmount(record.getArchivalExpense());
        monthlyHistoryResponse.setDate(getMonth(record.getData()));
        return monthlyHistoryResponse;
    }

    private String getMonth(String inputDate) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            DateTimeFormatter outputDate = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
            return date.format(outputDate);
        } catch (DateTimeParseException e) {
            logger.error("Failed to parse date: {}", inputDate, e);
            return null;
        }

    }

    public MonthlyHistory populateMonthlyHistory(AccountDetails accountDetails) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new MonthlyHistory(
                accountDetails.getUserDataAD(),
                currentDate.format(formatter),
                accountDetails.getExpenses(),
                accountDetails.getSavings(),
                accountDetails.getResidualFunds(),
                accountDetails.getAccountBalance()
        );

    }
}
