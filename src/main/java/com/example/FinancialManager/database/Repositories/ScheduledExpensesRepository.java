package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.transactions.ScheduledExpenses;
import com.example.FinancialManager.database.transactions.TransactionStatus;
import com.example.FinancialManager.database.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ScheduledExpensesRepository extends JpaRepository<ScheduledExpenses, Long> {

    List<ScheduledExpenses> findAllByUserDataSEAndTransactionStatus(UserData userData, TransactionStatus status);
}