package service;

import exception.*;
import model.Book;
import model.BookCopy;
import model.response.BookAvailabilityResponse;
import model.response.BookCopyResponse;
import model.response.BookResponse;
import repository.BookCopyRepository;
import repository.BookRepository;
import repository.LibraryBranchRepository;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.BOOK_COPY_CODE;

public class BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final LibraryBranchRepository libraryBranchRepository;
    private final Logger logger;

    private int bookCopyCount = 0;

    public BookService(BookRepository bookRepository, BookCopyRepository bookCopyRepository,
            LibraryBranchRepository libraryBranchRepository, Logger logger) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.libraryBranchRepository = libraryBranchRepository;
        this.logger = logger;
    }

    public Book getBook(String isbn) throws BookNotFoundException {
        return bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public void addBookCopy(String isbn, String branchCode) throws BranchNotFoundException, BookNotFoundException {
        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        var branch = libraryBranchRepository.getBranch(branchCode).orElseThrow(() -> new BranchNotFoundException(branchCode));

        bookCopyCount++;
        var bookCopy = new BookCopy(BOOK_COPY_CODE + bookCopyCount, book, branch);
        bookCopyRepository.addBookCopy(bookCopy);

        branch.addBookCopy(bookCopy);
        libraryBranchRepository.updateBranch(branch);

        logger.info("Book copy added: " + bookCopy.getId());
    }

    public void updateBook(String isbn, String title, List<String> authors, List<String> genres,
            Integer publicationYear)
            throws BookNotFoundException {
        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        book.setTitle(title);
        book.setAuthors(authors);
        book.setGenres(genres);
        book.setPublicationYear(publicationYear);

        bookRepository.updateBook(book);
        logger.info("Book updated: " + isbn);
    }

    public void removeBook(String isbn) throws BookInUseException, BookNotFoundException {
        var book = getBook(isbn);

        if (bookCopyRepository.isBookInUse(book.getId())) {
            throw new BookInUseException(book.getId());
        }

        bookRepository.removeBook(book.getId());
        bookCopyRepository.removeBookCopies(book.getId());
    }

    public List<BookResponse> searchBooks(String query, String searchBy) {
        var books = bookRepository.searchBooks(query, searchBy);
        if (books == null || books.isEmpty()) {
            return new ArrayList<>();
        }

        return books.stream().map(b -> new BookResponse(b.getId(), b.getTitle(), b.getAuthors(), b.getGenres(),
                b.getPublicationYear())).toList();
    }

    public BookCopy getBookCopy(String bookCopyId) throws BookCopyNotFoundException {
        return bookCopyRepository.getBookCopy(bookCopyId).orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
    }

    public void transferBookCopy(String bookItemId, String sourceCode, String destinationCode)
            throws BookCopyNotFoundException, BranchNotFoundException {
        var bookItem = bookCopyRepository.getBookCopy(bookItemId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookItemId));

        var source = libraryBranchRepository.getBranch(sourceCode).orElseThrow(() -> new BranchNotFoundException(sourceCode));
        var destination = libraryBranchRepository.getBranch(destinationCode).orElseThrow(() -> new BranchNotFoundException(destinationCode));

        source.removeBookCopy(bookItem);
        destination.addBookCopy(bookItem);
        bookItem.setBranch(destination);

        logger.info("Book item transferred: " + bookItem.getId() + " from " + source.getName() + " to "
                + destination.getName());
    }

    public BookAvailabilityResponse getBookAvailability(String isbn) throws BookNotFoundException {
        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        var totalCopies = bookCopyRepository.getTotalBookItemCount(book.getId());
        var availableCopies = bookCopyRepository.getAvailableBookCopiesCount(book.getId());
        var borrowedCopies = bookCopyRepository.getBorrowedBookItemCount(book.getId());

        return new BookAvailabilityResponse(book.getTitle(), totalCopies, availableCopies, borrowedCopies);
    }

    // Adds a new book to the book repository
    public void addBook(String isbn, String title, List<String> authors, List<String> genres, Integer publicationYear)
            throws BookExistsException {
        var response = bookRepository.getBook(isbn);
        if (response.isPresent()) {
            throw new BookExistsException(isbn);
        }

        var book = new Book(isbn, title, authors, genres, publicationYear);
        bookRepository.addBook(book);

        logger.info("Book added: " + book.getId());
    }

    public void transferAllBooks(String sourceCode, String destinationCode) throws BranchNotFoundException {
        var source = libraryBranchRepository.getBranch(sourceCode).orElseThrow(() -> new BranchNotFoundException(sourceCode));
        var destination = libraryBranchRepository.getBranch(destinationCode).orElseThrow(() -> new BranchNotFoundException(destinationCode));

        List<BookCopy> bookCopies = bookCopyRepository.getBookItemsByBranch(source.getId());
        int transferredCount = 0;

        for (BookCopy bookCopy : bookCopies) {
            bookCopy.setBranch(destination);
            source.removeBookCopy(bookCopy);
            destination.addBookCopy(bookCopy);
            bookCopyRepository.updateBookCopy(bookCopy);
            transferredCount++;
        }

        logger.info(transferredCount + " books transferred from " + source.getId() + " to " + destination.getId());
    }

    public BookCopyResponse printBookCopy(String bookItemId) throws BookCopyNotFoundException {
        var bookCopy = getBookCopy(bookItemId);

        return new BookCopyResponse(
                bookCopy.getId(),
                bookCopy.getBook().getTitle(),
                bookCopy.getBook().getAuthors(),
                bookCopy.getBranch().getName(),
                bookCopy.isAvailable()
        );
    }


    public List<BookResponse> getBorrowedBooks() {
        return bookCopyRepository.getBorrowedBookCopies().stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthors(), book.getGenres(), book.getPublicationYear()))
                .toList();
    }

    public List<BookResponse> getBorrowedBooksByBranch(String branchCode) throws BranchNotFoundException {
        var branch = libraryBranchRepository.getBranch(branchCode).orElseThrow(() -> new BranchNotFoundException(branchCode));;

        return bookCopyRepository.getBorrowedBookCopiesForBranch(branch).stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthors(), book.getGenres(), book.getPublicationYear()))
                .toList();
    }

    public List<BookResponse> getAvailableBooks() {
        return bookCopyRepository.getAvailableBookCopies().stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthors(), book.getGenres(), book.getPublicationYear()))
                .toList();
    }

    public List<BookResponse> getAvailableBooksByBranch(String branchCode) throws BranchNotFoundException {
        var branch = libraryBranchRepository.getBranch(branchCode).orElseThrow(() -> new BranchNotFoundException(branchCode));;
        return bookCopyRepository.getAvailableBookCopiesForBranch(branch).stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthors(), book.getGenres(), book.getPublicationYear()))
                .toList();
    }
}
