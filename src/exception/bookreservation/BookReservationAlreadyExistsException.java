package exception.bookreservation;

import exception.category.AlreadyExistsException;
import model.BookReservation;

public class BookReservationAlreadyExistsException extends AlreadyExistsException {
    public BookReservationAlreadyExistsException(final String identifier) {
        super(BookReservation.class.getSimpleName(), identifier);
    }
}
