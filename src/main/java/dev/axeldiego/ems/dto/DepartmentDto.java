package dev.axeldiego.ems.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Department", description = "Department data object")
public class DepartmentDto {
    @Schema(description = "Department unique identifier (auto-generated)", example = "1")
    private Long id;

    @NotBlank(message = "Department name is required")
    @Schema(description = "Department name", example = "Engineering")
    private String name;
}
