package dev.axeldiego.ems.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dev.axeldiego.ems.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "User authentication data")
public class UserDto {
    @NotBlank(message = "Username is required")
    @Schema(description = "Unique username for login", example = "admin")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "User password (not returned on response)", example = "password123")
    private String password;

    @Schema(description = "User role (ADMIN or USER)", example = "USER")
    private UserRole role;
}
