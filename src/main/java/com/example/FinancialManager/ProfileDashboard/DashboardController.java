package com.example.FinancialManager.ProfileDashboard;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/profile/dashboard")
public class DashboardController {

    DashboardUserService dashboardUserService;
    @GetMapping("/get-data/main-page")
    public ResponseEntity<?> getMainPageData(){
        try {
            return ResponseEntity.ok(dashboardUserService.getUserData());
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.internalServerError().body("Cannot fetch data");
        }
    }
}
