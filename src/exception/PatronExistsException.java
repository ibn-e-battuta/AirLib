package exception;

public class PatronExistsException extends Exception {
    public PatronExistsException(String email) {
        super("Patron with email " + email + " already exists");
    }
}
