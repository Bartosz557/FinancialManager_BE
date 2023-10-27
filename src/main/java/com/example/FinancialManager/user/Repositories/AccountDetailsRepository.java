package com.example.FinancialManager.user.Repositories;

import com.example.FinancialManager.user.accountDetails.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
}
