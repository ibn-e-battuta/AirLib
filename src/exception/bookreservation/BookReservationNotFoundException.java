package exception.bookreservation;

import exception.category.NotFoundException;
import model.BookReservation;

public class BookReservationNotFoundException extends NotFoundException {
    public BookReservationNotFoundException(final String bookReservationId) {
        super(BookReservation.class.getSimpleName(), bookReservationId);
    }
}