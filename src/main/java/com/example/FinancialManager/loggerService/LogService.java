package com.example.FinancialManager.loggerService;

import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class LogService {

    LocalDate currentDate = LocalDate.now();
    LocalTime currentTime = LocalTime.now();

    public void addLoginLog(String email) {
        addLog(email," logged in", "login_monitor.txt");
    }

    public void addRegistrationLog(String email) {
        addLog(email," was registered","registration_monitor.txt");
    }

    private void addLog(String email, String logType, String filename){
        try (FileWriter fw = new FileWriter("src/main/resources/"+filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            int hour = currentTime.getHour();
            int minute = currentTime.getMinute();
            int second = currentTime.getSecond();

            String currentTimeString = hour + ":" + minute + ":" + second;
            bw.write("User: " + email + logType + " successfully at: " + currentDate +" : "+currentTimeString);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
