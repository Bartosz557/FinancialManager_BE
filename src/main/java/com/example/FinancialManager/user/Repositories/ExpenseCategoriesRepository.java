package com.example.FinancialManager.user.Repositories;

import com.example.FinancialManager.user.transactions.ExpenseCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategories, Long> {
}