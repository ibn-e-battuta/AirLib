package service;

import exception.*;
import model.BookCheckout;
import model.BookReservation;
import model.Patron;
import model.ReservationStatus;
import model.response.BookCheckoutResponse;
import repository.*;
import util.Logger;

import java.time.LocalDate;
import java.util.List;

import static util.Constants.*;

public class ReservationService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final PatronRepository patronRepository;
    private final BookCheckoutRepository bookCheckoutRepository;
    private final BookReservationRepository bookReservationRepository;

    private final NotificationService notificationService;

    private final Logger logger;

    private int checkoutCount = 0;
    private int reservationCount = 0;

    public ReservationService(BookRepository bookRepository, BookCopyRepository bookCopyRepository, PatronRepository patronRepository, BookCheckoutRepository bookCheckoutRepository, BookReservationRepository bookReservationRepository, NotificationService notificationService,
                              Logger logger) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.patronRepository = patronRepository;
        this.bookCheckoutRepository = bookCheckoutRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.notificationService = notificationService;
        this.logger = logger;
    }

    // Checkout a copy of the book from a library's branch
    public void checkoutBook(String patronId, String bookCopyId, String branchCode)
            throws PatronNotFoundException, BookCopyNotFoundException, PatronMaxCheckoutsException,
            BookCopyUnavailableException {
        var patron = patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));

        if (getActiveCheckoutsForPatron(patron).size() >= MAX_CHECKOUTS) {
            throw new PatronMaxCheckoutsException(MAX_CHECKOUTS);
        }

        var bookCopy = bookCopyRepository.getBookCopy(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
        if (!bookCopy.isAvailable() || !bookCopy.getBranch().getId().equals(branchCode)) {
            throw new BookCopyUnavailableException(bookCopyId);
        }

        checkoutCount++;
        var bookCheckout = new BookCheckout(CHECKOUT_CODE + checkoutCount, patron, bookCopy,
                LocalDate.now());
        bookCheckoutRepository.addCheckout(bookCheckout);

        bookCopy.setAvailable(false);
        patron.addReservation(bookCheckout);

        bookCopyRepository.updateBookCopy(bookCopy);
        patronRepository.updatePatron(patron);

        var response = bookReservationRepository.getBookReservationByBookAndPatron(patron, bookCopy.getBook());
        if (response.isPresent()) {
            var bookReservation = response.get();
            bookReservation.setStatus(ReservationStatus.COMPLETED);
            bookReservationRepository.updateReservation(bookReservation);
        }

        logger.info("Book checked out with checkout Id: " + bookCheckout.getId());
    }

    // Return the book copy
    public void returnBook(String checkoutId) throws CheckoutNotFoundException {
        var bookCheckout = bookCheckoutRepository.getCheckout(checkoutId)
            .orElseThrow(() -> new CheckoutNotFoundException(checkoutId));

        bookCheckout.setReturnDate(LocalDate.now());
        var bookCopy = bookCheckout.getBookItem();
        bookCopy.setAvailable(true);

        bookCheckoutRepository.updateCheckout(bookCheckout);
        bookCopyRepository.updateBookCopy(bookCopy);

        logger.info("Book returned with checkout Id: " + bookCheckout.getId());

        var waitingReservations = bookReservationRepository.getWaitingReservations(bookCopy.getBook());

        if (!waitingReservations.isEmpty()) {
            var reservation = waitingReservations.get(0);
            notificationService.sendBookAvailableNotification(reservation.getPatron(), reservation.getBook());
        }
    }

    // Renew a book copy checkout
    public void renewBook(String checkoutId)
            throws CheckoutNotFoundException, BookCopyOverdueException, MaxRenewalsException {
        var bookCheckout = bookCheckoutRepository.getCheckout(checkoutId)
                .orElseThrow(() -> new CheckoutNotFoundException(checkoutId));
        if (bookCheckout.getDueDate().isBefore(LocalDate.now())) {
            throw new BookCopyOverdueException();
        }

        if (bookCheckout.getRenewalCount() >= MAX_RENEWALS) {
            throw new MaxRenewalsException(MAX_RENEWALS);
        }

        bookCheckout.setDueDate(bookCheckout.getDueDate().plusDays(15));
        bookCheckout.setRenewalCount(bookCheckout.getRenewalCount() + 1);

        bookCheckoutRepository.updateCheckout(bookCheckout);
        logger.info("Book renewed with checkout Id: " + bookCheckout.getId());
    }

    // Reserve a book
    public void reserveBook(String patronId, String isbn) throws PatronNotFoundException, BookCopyNotFoundException, PatronMaxCheckoutsException, BookCopyAlreadyCheckedOutException {
        var patron = patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));

        if (getActiveCheckoutsForPatron(patron).size() >= MAX_CHECKOUTS) {
            throw new PatronMaxCheckoutsException(MAX_CHECKOUTS);
        }

        var book = bookRepository.getBook(isbn).orElseThrow(() -> new BookCopyNotFoundException(isbn));

        var isBookCheckOutByPatron = bookCheckoutRepository.isBookCheckedOutByPatron(patron, book);
        if (isBookCheckOutByPatron) {
            throw new BookCopyAlreadyCheckedOutException(book.getTitle());
        }

        reservationCount++;
        var bookReservation = new BookReservation(BOOK_RESERVATION_CODE + reservationCount, patron, book);
        bookReservationRepository.addReservation(bookReservation);

        logger.info("Reservation added with id: " + bookReservation.getId());
    }

    public List<BookCheckout> getActiveCheckoutsForPatron(Patron patron) {
        return bookCheckoutRepository.getActiveCheckoutsForPatron(patron);
    }

    public List<BookCheckoutResponse> getPatronBorrowingHistory(String patronId) throws PatronNotFoundException {
        var patron = patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));
        return bookCheckoutRepository.getCheckoutsForPatron(patron).stream().map(c -> new BookCheckoutResponse(c.getBookItem().getBook().getTitle(), c.getIssueDate().toString(), c.getReturnDate().toString())).toList();
    }

    public void cancelReservation(String reservationId) throws ReservationNotFoundException, ReservationStatusException {
        var bookReservation = bookReservationRepository.getBookReservation(reservationId).orElseThrow(() -> new ReservationNotFoundException(reservationId));

        if (!bookReservation.getStatus().equals(ReservationStatus.WAITING)) {
            throw new ReservationStatusException();
        }

        bookReservation.setStatus(ReservationStatus.CANCELLED);
        bookReservationRepository.updateReservation(bookReservation);
        logger.info("Reservation cancelled with id: " + bookReservation.getId());
    }
}
