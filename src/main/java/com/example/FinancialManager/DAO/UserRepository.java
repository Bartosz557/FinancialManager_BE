package com.example.FinancialManager.DAO;

import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByEmail(String email);
    Optional<List<UserData>> findByUserRole(UserRole userRole);
    Optional<UserData>  findByUsername(String username);
    List<UserData> findAllByUserRole(UserRole userRole);

    @Transactional(readOnly = false)
    int deleteByUsername(String username);

}





