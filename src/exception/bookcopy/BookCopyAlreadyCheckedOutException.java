package exception.bookcopy;

import exception.category.InvalidOperationException;
import model.BookCopy;

public class BookCopyAlreadyCheckedOutException extends InvalidOperationException {
    public BookCopyAlreadyCheckedOutException(final String bookCopyId) {
        super(BookCopy.class.getSimpleName(), bookCopyId, "is currently checked out.");
    }
}