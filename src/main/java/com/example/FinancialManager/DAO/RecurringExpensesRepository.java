package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.RecurringExpenses;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionStatus;
import com.example.FinancialManager.DataModel.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RecurringExpensesRepository extends JpaRepository<RecurringExpenses, Long> {
    List<RecurringExpenses> findAllByUserDataRE(UserData userData);

    List<RecurringExpenses> findAllByUserDataREAndTransactionStatus(UserData userData, TransactionStatus transactionStatus);

}