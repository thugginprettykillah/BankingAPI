package sanchez.bankingapi.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Transfer request")
public record TransferRequestDto(

        @NotNull
        @Schema(description = "ID of sending account",
                example = "12")
        Long fromAccountId,

        @NotNull
        @Schema(description = "ID of getting account",
                example = "12")
        Long toAccountId,

        @NotNull
        @Positive
        @Schema(description = "Amount of transaction",
                example = "100.23")
        BigDecimal amount,

        @Schema(description = "Optional description")
        String description
) {
}