package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
    Optional<AccountDetails> findByUserDataAD(UserData user);
}
