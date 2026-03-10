package sanchez.bankingapi.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import sanchez.bankingapi.account.AccountCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Account information")
public record AccountResponseDto(

        @Schema(description = "Account ID",
                example = "1")
        Long id,

        @Schema(description = "Account number",
                example = "USD-21")
        String accountNumber,

        @Schema(description = "Current balance",
                example = "1000.40")
        BigDecimal balance,

        @Schema(description = "Account currency",
                example = "USD",
                allowableValues = {"RUB", "USD", "EUR"})
        AccountCurrency currency,

        @Schema(description = "Creation timestamp",
                example = "2026-03-03T12:00:00")
        LocalDateTime createdAt
)
{}
