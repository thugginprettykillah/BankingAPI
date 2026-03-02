package sanchez.bankingapi.exception;

public class TransferDirectionException extends RuntimeException {
    public TransferDirectionException(String message) {
        super(message);
    }
}
