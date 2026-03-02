package sanchez.bankingapi.exception;

public class TransferAmountException extends RuntimeException {
    public TransferAmountException(String message) {
        super(message);
    }
}
