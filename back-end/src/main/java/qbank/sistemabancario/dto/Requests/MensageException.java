package qbank.sistemabancario.dto.Requests;

public class MensageException extends RuntimeException {
    public MensageException(String message) {
        super(message);
    }
}
