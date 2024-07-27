package exception.patron;

import exception.category.AlreadyExistsException;
import model.Patron;

public class PatronExistsException extends AlreadyExistsException {
    public PatronExistsException(final String email) {
        super(Patron.class.getSimpleName(), email);
    }
}