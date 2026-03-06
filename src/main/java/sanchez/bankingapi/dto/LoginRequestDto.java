package sanchez.bankingapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Login request")
public record LoginRequestDto(
        @NotNull
        @NotBlank
        @Email
        @Schema(description = "User email", example = "boss@gmail.com")
        String email,
        @NotNull
        @NotBlank
        @Schema(description = "User password", example = "StrongPassword123")
        String password)
{}