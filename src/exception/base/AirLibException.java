package exception.base;

public abstract class AirLibException extends RuntimeException {
    protected AirLibException(final String message) {
        super(message);
    }
}