package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.userHistory.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Optional<List<TransactionHistory>> findAllByUserData(UserData userData);
}