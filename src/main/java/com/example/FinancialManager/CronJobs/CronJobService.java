package com.example.FinancialManager.CronJobs;

import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.user.UserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Service
public class CronJobService {

    private final UserRepository userRepository;
    public void monthlyResetJob(){
        List<UserData> userList = userRepository.findAll();
        for( UserData user: userList){
            addMonthlyHistory(user);
            userSettlement(user);
        }

    }

    private void addMonthlyHistory(UserData user) {

    }
}
