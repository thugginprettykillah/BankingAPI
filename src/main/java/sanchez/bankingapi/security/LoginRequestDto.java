package sanchez.bankingapi.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        String password)
{}