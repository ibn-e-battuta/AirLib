package exception;

public class BookItemUnavailableException extends Exception {
    public BookItemUnavailableException(String message) {
        super(message);
    }
}
