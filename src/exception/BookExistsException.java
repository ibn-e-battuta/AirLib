package exception;

public class BookExistsException extends Exception {
    
    public BookExistsException(String isbn) {
        super("Book with ISBN " + isbn + " exists.");
    }
}
