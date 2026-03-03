package sanchez.bankingapi.transaction;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferRequestDto(

        @NotNull
        @NotBlank
        Long fromAccountId,

        @NotNull
        @NotBlank
        Long toAccountId,

        @NotNull
        @NotBlank
        @Positive
        BigDecimal amount,


        String description
) {
}
