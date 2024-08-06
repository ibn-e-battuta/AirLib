package exception.book;

import exception.category.NotFoundException;
import model.Book;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(final String isbn) {
        super(Book.class.getSimpleName(), isbn);
    }
}