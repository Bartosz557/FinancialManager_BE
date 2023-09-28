    package com.example.FinancialManager;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.bind.annotation.CrossOrigin;


    @RestController
    public class AngularController {
        @GetMapping("api/v1/home")
        public String getData() {
            return "welcome to main page";
        }
    }

