package sanchez.bankingapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login response")
public record LoginResponseDto(
        @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token
) {
}
