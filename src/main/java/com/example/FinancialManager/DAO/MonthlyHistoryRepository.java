package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.MonthlyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MonthlyHistoryRepository extends JpaRepository<MonthlyHistory, Long> {
    Optional<List<MonthlyHistory>> findAllByUserData(UserData userData);
}