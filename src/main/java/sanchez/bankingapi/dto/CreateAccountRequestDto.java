package sanchez.bankingapi.dto;

import jakarta.validation.constraints.NotNull;
import sanchez.bankingapi.model.AccountCurrency;

public record CreateAccountRequestDto (
        @NotNull
        AccountCurrency currency
)
{}
