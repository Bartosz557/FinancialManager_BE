package com.example.FinancialManager.user.Repositories;

import com.example.FinancialManager.user.userHistory.MonthlyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
interface MonthlyHistoryRepository extends JpaRepository<MonthlyHistory, Long> {
}