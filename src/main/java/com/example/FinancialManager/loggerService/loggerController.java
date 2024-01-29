package com.example.FinancialManager.loggerService;

import com.example.FinancialManager.database.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("admin/logger")
public class loggerController {

    UserRepository userRepository;
    @GetMapping("/login-logs")
    public ResponseEntity<Resource> getLoginLogs(){
        Resource resource = new ClassPathResource("login_monitor.txt");

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/registration-logs")
    public ResponseEntity<Resource> getRegLogs(){
        Resource resource = new ClassPathResource("registration_monitor.txt");

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
