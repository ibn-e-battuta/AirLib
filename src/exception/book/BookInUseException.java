package exception.book;

import exception.category.InvalidOperationException;
import model.Book;

public class BookInUseException extends InvalidOperationException {
    public BookInUseException(final String isbn) {
        super(Book.class.getSimpleName(), isbn, "cannot be deleted, as the book's copy is currently checked out");
    }
}