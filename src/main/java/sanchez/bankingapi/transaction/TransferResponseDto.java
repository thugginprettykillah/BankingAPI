package sanchez.bankingapi.transaction;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transfer response information")
public record TransferResponseDto(

        @Schema(description = "Transaction id", example = "12")
        Long id,

        @Schema(description = "Sending account number", example = "USD-123")
        String fromAccountNumber,

        @Schema(description = "Getting account number", example = "USD-123")
        String toAccountNumber,

        @Schema(description = "Status of transfer",
                example = "APPROVED",
                allowableValues = {"APPROVED", "REJECTED", "PENDING"})
        MoneyTransactionStatus status,

        @Schema(description = "Optional description from user")
        String message
) {
}
