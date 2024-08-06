package exception.bookreservation;

import exception.category.InvalidOperationException;
import model.BookReservation;

public class BookReservationStatusException extends InvalidOperationException {
    public BookReservationStatusException(final String bookReservationId) {
        super(BookReservation.class.getSimpleName(), bookReservationId, "cannot be cancelled due to its current status");
    }
}