package sanchez.bankingapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Error response")
public record ErrorResponseDto (
        @Schema(description = "Error title", example = "Validation Error")
        String message,

        @Schema(description = "Error details",
                example = "{email=must be a well-formed email address}")
        String detailedMessage,

        @Schema(description = "Timestamp of error",
                example = "2026-03-05T12:55:23")
        LocalDateTime timestamp
) {
}
