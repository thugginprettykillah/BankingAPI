package sanchez.bankingapi.transaction;

public record TransferResponseDto(
        Long id,
        String fromAccountNumber,
        String toAccountNumber,
        MoneyTransactionStatus status,
        String message
) {
}
