package dev.axeldiego.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/health")
class HealthController {
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("EMS API is healthy");
    }
}
