package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.transactions.RecurringExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
interface RecurringExpensesRepository extends JpaRepository<RecurringExpenses, Long> {
}