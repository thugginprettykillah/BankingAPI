package sanchez.bankingapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response of registration")
public record RegistrationResponseDto(
        @Schema(description = "ID of created user",
                example = "1")
        Long id
) {
}
