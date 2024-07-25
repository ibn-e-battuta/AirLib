package exception;

public class PatronNotFoundException extends Exception {
    public PatronNotFoundException(String patronId) {
        super("Patron with id: " + patronId + " not found.");
    }
}
