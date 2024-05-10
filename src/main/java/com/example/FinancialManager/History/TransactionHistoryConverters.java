package com.example.FinancialManager.History;

import com.example.FinancialManager.AdminCockpit.AdminController;
import com.example.FinancialManager.database.transactions.TransactionType;
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
import java.util.Date;

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
}
