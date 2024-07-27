package exception.bookcopy;

import exception.category.InvalidOperationException;
import model.Patron;

public class BookCopyCheckedOutException extends InvalidOperationException {
    public BookCopyCheckedOutException(String identifier) {
        super(Patron.class.getSimpleName(), identifier, "has already checked out this book or book's copy");
    }
}
