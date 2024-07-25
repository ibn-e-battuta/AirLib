package exception;

public class BookCopyAlreadyCheckedOutException extends Exception {
    public BookCopyAlreadyCheckedOutException(String title) {
        super("Cannot reverse book: " + title + ". It is currently checked out.");
    }
}
