package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.LimitDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface LimitDetailsRepository extends JpaRepository<LimitDetails, Long> {
}
