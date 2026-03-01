package sanchez.bankingapi.account;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDto(
        @NotNull
        Long id,
        @NotNull
        String accountNumber,
        @NotNull
        BigDecimal balance,
        @NotNull
        AccountCurrency currency,
        @NotNull
        LocalDateTime createdAt
                          )
{}
