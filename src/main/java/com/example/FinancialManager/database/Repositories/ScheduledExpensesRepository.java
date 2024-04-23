package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.transactions.ScheduledExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ScheduledExpensesRepository extends JpaRepository<ScheduledExpenses, Long> {
}