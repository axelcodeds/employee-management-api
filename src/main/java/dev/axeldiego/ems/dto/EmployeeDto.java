package dev.axeldiego.ems.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import dev.axeldiego.ems.entity.EmployeeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Employee", description = "Employee data object with all details")
public class EmployeeDto {
    @Schema(description = "Employee unique identifier (auto-generated)")
    private Long id;

    @NotBlank(message = "First name is required")
    @Schema(description = "Employee's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Employee's last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Employee's email address", example = "john.doe@example.com")
    private String email;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than zero")
    @Schema(description = "Employee's salary", example = "50000.00")
    private BigDecimal salary;

    @NotNull(message = "Department is required")
    @Schema(description = "Employee's department")
    private DepartmentDto department;

    @Schema(description = "Employee's current status (ACTIVE or INACTIVE)", example = "ACTIVE")
    private EmployeeStatus status;

    @Schema(description = "Timestamp when employee record was created (auto-generated)", example = "2026-05-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when employee record was last updated (auto-generated)", example = "2026-05-01T15:30:00")
    private LocalDateTime updatedAt;
}
