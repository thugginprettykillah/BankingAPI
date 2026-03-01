package sanchez.bankingapi.account;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDto(
        Long id,

        String accountNumber,

        BigDecimal balance,

        AccountCurrency currency,

        LocalDateTime createdAt
                          )
{}
