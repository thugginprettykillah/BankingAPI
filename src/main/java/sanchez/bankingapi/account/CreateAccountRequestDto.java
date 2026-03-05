package sanchez.bankingapi.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create new account")
public record CreateAccountRequestDto (

        @NotNull
        @Schema(description = "Account currency",
                example = "USD",
                required = true)
        AccountCurrency currency
)
{}
