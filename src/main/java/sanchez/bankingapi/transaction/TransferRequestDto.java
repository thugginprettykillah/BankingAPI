package sanchez.bankingapi.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;

public record TransferRequestDto(

        @NotNull
        Long fromAccountId,
        @NotNull
        Long toAccountId,
        @NotNull
        @Min(10)
        BigDecimal amount,

        String description
) {
}
