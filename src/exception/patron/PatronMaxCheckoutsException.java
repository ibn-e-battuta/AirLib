package exception.patron;

import exception.category.LimitExceededException;
import model.Patron;

public class PatronMaxCheckoutsException extends LimitExceededException {
    public PatronMaxCheckoutsException(final String patronId, final int maxCheckouts) {
        super(Patron.class.getSimpleName(), patronId, maxCheckouts, "checkouts");
    }
}