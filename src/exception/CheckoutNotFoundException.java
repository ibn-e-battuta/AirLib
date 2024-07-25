package exception;

public class CheckoutNotFoundException extends Exception {
    public CheckoutNotFoundException(String message) {
        super(message);
    }
}
