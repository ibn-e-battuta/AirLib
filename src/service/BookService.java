package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.book.BookExistsException;
import exception.book.BookInUseException;
import exception.book.BookNotFoundException;
import model.Book;
import model.BookCopy;
import repository.contract.IBookCopyRepository;
import repository.contract.IBookRepository;
import response.BookAvailabilityResponse;
import response.BookResponse;
import util.Logger;

public class BookService {
    private final IBookRepository bookRepository;
    private final IBookCopyRepository bookCopyRepository;
    private final Logger logger;

    public BookService(IBookRepository bookRepository, IBookCopyRepository bookCopyRepository, Logger logger) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.logger = logger;
    }

    public BookResponse getBook(final String isbn) {
        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        return new BookResponse(book);
    }

    public void updateBook(final String isbn, final String title, final List<String> authors, final List<String> genres,
            final Integer publicationYear) {
        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        book.setTitle(title);
        book.setAuthors(authors);
        book.setGenres(genres);
        book.setPublicationYear(publicationYear);

        bookRepository.update(book);
        logger.info("Book updated: " + isbn);
    }

    public void removeBook(final String isbn) {
        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        if (bookCopyRepository.isBookInUse(book.getId())) {
            throw new BookInUseException(book.getId());
        }

        bookRepository.remove(book.getId());
        bookCopyRepository.removeAll(book.getId());
    }

    public List<BookResponse> searchBooks(final String query, final String searchBy) {
        final List<Book> books = bookRepository.search(query, searchBy);
        if (books == null || books.isEmpty()) {
            return new ArrayList<>();
        }

        return books.stream().map(BookResponse::new).toList();
    }

    public BookAvailabilityResponse getBookAvailability(final String isbn) {
        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        final List<BookCopy> bookCopies = bookCopyRepository.getAllByIsbn(isbn);

        int totalCopies = bookCopies.size();
        int availableCopies = (int) bookCopies.stream().filter(BookCopy::isAvailable).count();
        int borrowedCopies = (int) bookCopies.stream().filter(b -> !b.isAvailable()).count();

        return new BookAvailabilityResponse(book.getTitle(), totalCopies, availableCopies, borrowedCopies);
    }

    // Adds a new book to the book repository
    public void addBook(final String isbn, final String title, final List<String> authors, final List<String> genres,
            final int publicationYear) {
        final Optional<Book> optionalBook = bookRepository.getById(isbn);
        if (optionalBook.isPresent()) {
            throw new BookExistsException(isbn);
        }

        final Book book = new Book(isbn, title, authors, genres, publicationYear);
        bookRepository.add(book);

        logger.consoleOutput("Book added: " + book.getId());
    }
}
