package com.kakaooauth2service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class HealthCheckController {

    @GetMapping("/health/check")
    public String healthCheck() {
        return "check!";
    }

    @GetMapping("/check")
    public String healthCheckSecurity() {
        return "check!!";
    }

}
