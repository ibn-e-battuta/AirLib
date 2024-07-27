package exception.bookcopy;

import exception.category.InvalidOperationException;
import model.BookCopy;

public class BookCopyUnavailableException extends InvalidOperationException {
    public BookCopyUnavailableException(final String bookCopyId) {
        super(BookCopy.class.getSimpleName(), bookCopyId, "is not available for checkout at the specified branch");
    }
}