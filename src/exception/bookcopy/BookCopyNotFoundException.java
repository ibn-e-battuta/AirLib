package exception.bookcopy;

import exception.category.NotFoundException;
import model.BookCopy;

public class BookCopyNotFoundException extends NotFoundException {
    public BookCopyNotFoundException(final String bookCopyId) {
        super(BookCopy.class.getSimpleName(), bookCopyId);
    }
}