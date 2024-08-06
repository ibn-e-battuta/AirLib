package exception.book;

import exception.category.AlreadyExistsException;
import model.Book;

public class BookExistsException extends AlreadyExistsException {
    public BookExistsException(final String isbn) {
        super(Book.class.getSimpleName(), isbn);
    }
}