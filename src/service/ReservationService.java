package service;

import java.time.LocalDate;
import java.util.List;

import exception.BookItemNotFoundException;
import exception.BookItemUnavailableException;
import exception.LibraryException;
import exception.MaxRenewalsException;
import exception.PatronMaxCheckoutsException;
import exception.PatronNotFoundException;
import exception.ReservationNotFoundException;
import model.Book;
import model.BookItem;
import model.BookReservation;
import model.Patron;
import model.Reservation;
import model.ReservationStatus;
import repository.BookRepository;
import repository.PatronRepository;
import repository.ReservationRepository;
import util.IdGenerator;
import util.Logger;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final NotificationService notificationService;
    private final Logger logger;
    private static final int MAX_RENEWALS = 2;

    private static String reservationCode = "R";
    private int reservationCount = 1;

    public ReservationService(ReservationRepository reservationRepository, NotificationService notificationService,
            BookRepository bookRepository, PatronRepository patronRepository, Logger logger) {
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.logger = logger;
    }

    public void checkoutBook(String patronId, String bookItemId, String branchId)
            throws PatronNotFoundException, BookItemNotFoundException, PatronMaxCheckoutsException,
            BookItemUnavailableException {
        var patron = patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));

        if (getActiveReservationsForPatron(patron).size() >= 10) {
            throw new PatronMaxCheckoutsException("Patron has reached the maximum number of checkouts");
        }

        var bookItem = bookRepository.getBookItem(bookItemId)
                .orElseThrow(() -> new BookItemNotFoundException(bookItemId));
        if (!bookItem.isAvailable() || !bookItem.getBranch().getId().equals(branchId)) {
            throw new BookItemUnavailableException("Book is not available for checkout at the specified branch");
        }

        reservationCount++;
        Reservation reservation = new Reservation(reservationCode + reservationCount, patron, bookItem,
                LocalDate.now());
        reservationRepository.addReservation(reservation);
        bookItem.setAvailable(false);
        patron.addReservation(reservation);

        bookRepository.updateBookItem(bookItem);
        patronRepository.updatePatron(patron);

        logger.info("Book checked out with reservation Id: " + reservation.getId());
    }

    public void returnBook(String reservationId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.getReservation(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        // Set the return date and make the book item available
        reservation.setReturnDate(LocalDate.now());
        BookItem bookItem = reservation.getBookItem();
        bookItem.setAvailable(true);

        // Update the reservation and book item in the repositories
        reservationRepository.updateReservation(reservation);
        bookRepository.updateBookItem(bookItem);

        // Log the return
        logger.info("Book returned: " + bookItem.getBook().getTitle() + " by patron: " + reservation.getPatron().getName());

        // Check for waiting reservations
        List<BookReservation> waitingReservations = reservationRepository.getWaitingReservations(bookItem.getBook());
        if (!waitingReservations.isEmpty()) {
            BookReservation nextReservation = waitingReservations.get(0);
            notificationService.sendBookAvailableNotification(nextReservation.getPatron(), bookItem.getBook());
        }
    }

    public void renewBook(String reservationId)
            throws ReservationNotFoundException, BookItemOverdueException, MaxRenewalsException {
        Reservation reservation = reservationRepository.getReservation(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        if (reservation.getDueDate().isBefore(LocalDate.now())) {
            throw new BookItemOverdueException("Book is overdue and cannot be renewed");
        }

        if (reservation.getRenewalCount() >= MAX_RENEWALS) {
            throw new MaxRenewalsException("Maximum number of renewals reached");
        }

        reservation.setDueDate(reservation.getDueDate().plusDays(15));
        reservation.setRenewalCount(reservation.getRenewalCount() + 1);
        logger.info("Reservation Id: " + reservation.getId() + ": " + "Book renewed: "
                + reservation.getBookItem().getId() + " by patron: "
                + reservation.getPatron().getId());
    }

    public BookReservation reserveBook(Patron patron, Book book) throws LibraryException {
        if (getActiveReservationsForPatron(patron).size() >= 10) {
            throw new LibraryException("Patron has reached the maximum number of reservations");
        }

        String reservationId = IdGenerator.generateId();
        BookReservation bookReservation = new BookReservation(reservationId, patron, book);
        reservationRepository.addBookReservation(bookReservation);

        logger.info("Book reserved: " + book.getIsbn() + " by patron: " + patron.getId());
        return bookReservation;
    }

    public List<Reservation> getActiveReservationsForPatron(Patron patron) {
        return reservationRepository.getActiveReservationsForPatron(patron);
    }

    public List<Reservation> getOverdueReservations() {
        return reservationRepository.getOverdueReservations();
    }

    public void checkOverdueBooks() {
        List<Reservation> overdueReservations = getOverdueReservations();
        for (Reservation reservation : overdueReservations) {
            notificationService.sendOverdueNotification(reservation.getPatron(), reservation.getBookItem());
        }
    }

    public void checkUpcomingDueBooks() {
        LocalDate twoDaysLater = LocalDate.now().plusDays(2);
        List<Reservation> upcomingDueReservations = reservationRepository.getReservationsWithDueDate(twoDaysLater);
        for (Reservation reservation : upcomingDueReservations) {
            notificationService.sendDueDateReminder(reservation.getPatron(), reservation.getBookItem());
        }
    }

    public void checkAndFulfillReservations() {
        List<BookReservation> activeReservations = reservationRepository.getAllActiveBookReservations();
        for (BookReservation reservation : activeReservations) {
            List<BookItem> availableItems = bookRepository.getAvailableBookItems(reservation.getBook().getIsbn(),
                    reservation.getPatron().getId());
            if (!availableItems.isEmpty()) {
                BookItem availableItem = availableItems.get(0);
                try {
                    Reservation newReservation = checkoutBook(reservation.getPatron(), availableItem,
                            availableItem.getBranch().getId());
                    reservation.setStatus(ReservationStatus.COMPLETED);
                    reservationRepository.updateBookReservation(reservation);
                    notificationService.sendReservationFulfilledNotification(reservation.getPatron(),
                            availableItem.getBook());
                } catch (LibraryException e) {
                    logger.error("Failed to fulfill reservation: " + e.getMessage());
                }
            }
        }
    }

    public Reservation getReservation(String reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    /*
     * public void checkAndFulfillReservations() {
     * List<BookReservation> activeReservations =
     * reservationRepository.getAllActiveBookReservations();
     * for (BookReservation reservation : activeReservations) {
     * List<BookItem> availableItems =
     * bookRepository.getAvailableBookItems(reservation.getBook().getIsbn());
     * if (!availableItems.isEmpty()) {
     * BookItem availableItem = availableItems.get(0);
     * try {
     * Reservation newReservation = checkoutBook(reservation.getPatron(),
     * availableItem);
     * reservation.setStatus(BookReservation.ReservationStatus.COMPLETED);
     * reservationRepository.updateBookReservation(reservation);
     * notificationService.sendReservationFulfilledNotification(reservation.
     * getPatron(), availableItem.getBook());
     * } catch (LibraryException e) {
     * logger.error("Failed to fulfill reservation: " + e.getMessage());
     * }
     * }
     * }
     * }
     * 
     */

}
