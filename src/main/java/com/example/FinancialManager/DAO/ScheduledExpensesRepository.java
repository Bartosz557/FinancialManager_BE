package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.ScheduledExpenses;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionStatus;
import com.example.FinancialManager.DataModel.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ScheduledExpensesRepository extends JpaRepository<ScheduledExpenses, Long> {

    List<ScheduledExpenses> findAllByUserDataSEAndTransactionStatus(UserData userData, TransactionStatus status);

}