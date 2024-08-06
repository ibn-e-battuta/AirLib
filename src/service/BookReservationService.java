package service;

import static util.Constants.MAX_CHECKOUTS;

import java.util.Optional;

import exception.bookcopy.BookCopyAlreadyCheckedOutException;
import exception.bookcopy.BookCopyNotFoundException;
import exception.bookreservation.BookReservationAlreadyExistsException;
import exception.bookreservation.BookReservationNotFoundException;
import exception.bookreservation.BookReservationStatusException;
import exception.patron.PatronMaxCheckoutsException;
import exception.patron.PatronNotFoundException;
import model.Book;
import model.BookReservation;
import model.BookReservationStatus;
import model.Patron;
import repository.contract.IBookRepository;
import repository.contract.IBookReservationRepository;
import repository.contract.IPatronRepository;
import service.enums.EntityCode;
import util.Logger;

public class BookReservationService {
    private final IBookRepository bookRepository;
    private final IPatronRepository patronRepository;
    private final IBookReservationRepository bookReservationRepository;

    private final Logger logger;

    private int bookReservationCount = 0;

    public BookReservationService(IBookRepository bookRepository, IPatronRepository patronRepository,
            IBookReservationRepository bookReservationRepository,
            Logger logger) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.logger = logger;
    }

    // Reserve a book
    public void addBookReservation(final String patronId, final String isbn) {
        final Patron patron = patronRepository.getById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        if (patron.getBookCheckouts().size() >= MAX_CHECKOUTS) {
            throw new PatronMaxCheckoutsException(patronId, MAX_CHECKOUTS);
        }

        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookCopyNotFoundException(isbn));

        final boolean isBookCheckedOutByPatron = patron.isBookCheckedOut(book);
        if (isBookCheckedOutByPatron) {
            throw new BookCopyAlreadyCheckedOutException(book.getTitle());
        }

        final Optional<BookReservation> optionalBookReservation = bookReservationRepository.getByBookAndPatron(patron,
                book);
        if (optionalBookReservation.isPresent()) {
            throw new BookReservationAlreadyExistsException(optionalBookReservation.get().getId());
        }

        bookReservationCount++;
        final BookReservation bookReservation = new BookReservation(
                EntityCode.BOOK_RESERVATION.getCode() + bookReservationCount, patron, book);
        bookReservationRepository.add(bookReservation);

        logger.info("Book reservation added with id: " + bookReservation.getId());
    }

    public void cancelBookReservation(final String bookReservationId) {
        final BookReservation bookReservation = bookReservationRepository.getById(bookReservationId)
                .orElseThrow(() -> new BookReservationNotFoundException(bookReservationId));

        if (!bookReservation.getStatus().equals(BookReservationStatus.WAITING)) {
            throw new BookReservationStatusException(bookReservationId);
        }

        bookReservation.setStatus(BookReservationStatus.CANCELLED);
        bookReservationRepository.update(bookReservation);
        logger.info("Book reservation cancelled with id: " + bookReservation.getId());
    }
}