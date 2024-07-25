package exception;

public class BookCopyUnavailableException extends Exception {
    public BookCopyUnavailableException(String bookItemId) {
        super("Book item with Id " + bookItemId + " is not available for checkout at the specified branch");
    }
}
