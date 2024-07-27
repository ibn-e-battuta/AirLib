package exception.bookcopy;

import exception.category.InvalidOperationException;
import model.BookCopy;

public class BookCopyOverdueException extends InvalidOperationException {
    public BookCopyOverdueException(final String bookCopyId) {
        super(BookCopy.class.getSimpleName(), bookCopyId, "is overdue and cannot be renewed");
    }
}