package dev.axeldiego.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "API for checking application health")
public class HealthController {
    @Operation(summary = "Check API health", description = "Returns the health status of the API (no authentication required)")
    @ApiResponse(responseCode = "200", description = "API is healthy")
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("EMS API is healthy");
    }
}
