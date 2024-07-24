package service;

import java.util.List;
import java.util.Map;

import exception.BookExistsException;
import exception.BookInUseException;
import exception.BookItemNotFoundException;
import exception.BookNotFoundException;
import exception.BranchNotFoundException;
import model.Book;
import model.BookItem;
import repository.BookRepository;
import util.Logger;

public class BookService {
    private final LibraryService libraryService;
    private final BookRepository bookRepository;
    private final Logger logger;

    private static String bookItemCode = "BI";
    private int bookItemCount = 0;

    public BookService(BookRepository bookRepository, LibraryService libraryService, Logger logger) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
        this.logger = logger;
    }

    public Book getBook(String isbn) throws BookNotFoundException {
        return bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public void addBookItem(String isbn, String branchId) throws BranchNotFoundException, BookNotFoundException {
        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        var branch = libraryService.getBranch(branchId);

        bookItemCount++;
        var bookItem = new BookItem(bookItemCode + bookItemCount, book, branch);
        bookRepository.addBookItem(bookItem);
        branch.addBookItem(bookItem);

        logger.info("Book item added: " + bookItem.getId());
    }

    public void updateBook(String isbn, String title, String author, String subject, Integer publicationYear)
            throws BookNotFoundException {
        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        book.setTitle(title);
        book.setAuthor(author);
        book.setSubject(subject);
        book.setPublicationYear(publicationYear);

        bookRepository.updateBook(book);

        logger.info("Book updated: " + isbn);
    }

    public void removeBook(String isbn) throws BookInUseException, BookNotFoundException {
        if (bookRepository.isBookInUse(isbn)) {
            throw new BookInUseException("Cannot delete book. It is currently checked out.");
        }

        bookRepository.removeBook(isbn);
    }

    public List<Book> searchBooks(String query, String searchBy) {
        return bookRepository.searchBooks(query, searchBy);
    }

    public BookItem getBookItem(String bookItemId) throws BookItemNotFoundException {
        return bookRepository.getBookItem(bookItemId).orElseThrow(() -> new BookItemNotFoundException(bookItemId));
    }

    public void transferBookItem(String bookItemId, String sourceId, String destinationId)
            throws BookItemNotFoundException, BranchNotFoundException {
        var bookItem = bookRepository.getBookItem(bookItemId)
                .orElseThrow(() -> new BookItemNotFoundException(bookItemId));

        var source = libraryService.getBranch(sourceId);
        var destination = libraryService.getBranch(destinationId);

        source.removeBookItem(bookItem);
        destination.addBookItem(bookItem);
        bookItem.setBranch(destination);

        logger.info("Book item transferred: " + bookItem.getId() + " from " + source.getName() + " to "
                + destination.getName());
    }

    public int getTotalCopies(String isbn) {
        return bookRepository.getTotalCopies(isbn);
    }

    public int getLentCopies(String isbn) {
        return bookRepository.getLentCopies(isbn);
    }

    public Map<String, Integer> getBookCopiesByBranch(String isbn) {
        return bookRepository.getBookCopiesByBranch(isbn);
    }

    public void addBook(String isbn, String title, String author, String subject, Integer publicationDate)
            throws BookExistsException {
        var response = bookRepository.getBook(isbn);
        if (response.isPresent())
            throw new BookExistsException(isbn);
        Book book = new Book(isbn, title, author, subject, publicationDate);
        bookRepository.addBook(book);
        logger.info("Book added " + book.getIsbn());
    }

    public void transferAllBooks(String sourceId, String destinationId) throws BranchNotFoundException {
        var source = libraryService.getBranch(sourceId);
        var destination = libraryService.getBranch(destinationId);

        List<BookItem> bookItems = bookRepository.getBookItemsByBranch(source.getId());
        int transferredCount = 0;

        for (BookItem bookItem : bookItems) {
            bookItem.setBranch(destination);
            source.removeBookItem(bookItem);
            destination.addBookItem(bookItem);
            bookRepository.updateBookItem(bookItem);
            transferredCount++;
        }

        logger.info(transferredCount + " books transferred from " + source.getId() + " to " + destination.getId());
    }
}
