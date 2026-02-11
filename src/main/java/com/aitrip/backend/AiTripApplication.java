package com.aitrip.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AiTripApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiTripApplication.class, args);
    }
}