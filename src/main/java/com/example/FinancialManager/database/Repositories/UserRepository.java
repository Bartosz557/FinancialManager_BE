package com.example.FinancialManager.database.Repositories;

import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Transactional(readOnly = false)
    int deleteByUsername(String username);

    @Transactional(readOnly = false)
    int deleteByUserID(Long userID);


//    @Modifying
//    @Transactional(readOnly = false)
//    @Query("DELETE FROM UserData t WHERE t.userID = :id")
//    int deleteByUserID(@Param("id") Long theId);

}
