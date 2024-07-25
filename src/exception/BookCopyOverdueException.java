package exception;

public class BookCopyOverdueException extends Exception {
    public BookCopyOverdueException() {
        super("Book is overdue and cannot be renewed");
    }
}
