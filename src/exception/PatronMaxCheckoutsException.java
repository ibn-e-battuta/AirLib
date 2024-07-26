package exception;

public class PatronMaxCheckoutsException extends Exception {
    public PatronMaxCheckoutsException(int maxCheckouts) {
        super("Patron has reached the maximum number (" + maxCheckouts + ") of checkouts");
    }
}
