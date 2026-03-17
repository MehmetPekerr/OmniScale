package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
    
    @RestController
    public static class TestController {

        @GetMapping("/")
        public String home() {
            // Çevre değişkeninden APP_NAME değerini al
            String appName = System.getenv("APP_NAME");
            
            if (appName == null) {
                appName = "Bilinmeyen Uygulama"; // Eğer çevre değişkeni yoksa bir default değer
            }
            
            return "Uygulama doğru çalışıyor! - " + appName;
        }
    }
}
