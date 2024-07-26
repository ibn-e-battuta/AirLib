package exception;

public class BookInUseException extends Exception {
    
    public BookInUseException(String isbn) {
        super("Cannot delete book with ISBN: " + isbn + ". It is currently checked out.");
    }

}
