package sanchez.bankingapi.account;

import jakarta.validation.constraints.NotNull;

public record CreateAccountRequestDto (
        @NotNull
        AccountCurrency currency
)
{}
