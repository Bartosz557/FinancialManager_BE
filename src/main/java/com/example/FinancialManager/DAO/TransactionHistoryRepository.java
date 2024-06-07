package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.TransactionHistory;
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