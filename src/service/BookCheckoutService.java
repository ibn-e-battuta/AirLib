package service;

import static util.Constants.MAX_CHECKOUTS;
import static util.Constants.MAX_RENEWALS;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import exception.bookcheckout.BookCheckoutMaxRenewalsException;
import exception.bookcheckout.BookCheckoutNotFoundException;
import exception.bookcopy.BookCopyCheckedOutException;
import exception.bookcopy.BookCopyNotFoundException;
import exception.bookcopy.BookCopyOverdueException;
import exception.bookcopy.BookCopyUnavailableException;
import exception.librarybranch.LibraryBranchNotFoundException;
import exception.patron.PatronMaxCheckoutsException;
import exception.patron.PatronNotFoundException;
import model.BookCheckout;
import model.BookCopy;
import model.BookReservation;
import model.BookReservationStatus;
import model.LibraryBranch;
import model.Patron;
import repository.contract.IBookCheckoutRepository;
import repository.contract.IBookCopyRepository;
import repository.contract.IBookReservationRepository;
import repository.contract.ILibraryBranchRepository;
import repository.contract.IPatronRepository;
import service.enums.EntityCode;
import util.Logger;

public class BookCheckoutService {
    private final IBookCopyRepository bookCopyRepository;
    private final IPatronRepository patronRepository;
    private final IBookCheckoutRepository bookCheckoutRepository;
    private final IBookReservationRepository bookReservationRepository;
    private final ILibraryBranchRepository libraryBranchRepository;

    private final NotificationService notificationService;

    private final Logger logger;

    private int bookCheckoutCount = 0;

    public BookCheckoutService(IBookCopyRepository bookCopyRepository,
            IPatronRepository patronRepository, IBookCheckoutRepository bookCheckoutRepository,
            IBookReservationRepository bookReservationRepository, ILibraryBranchRepository libraryBranchRepository,
            NotificationService notificationService,
            Logger logger) {
        this.bookCopyRepository = bookCopyRepository;
        this.patronRepository = patronRepository;
        this.bookCheckoutRepository = bookCheckoutRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.libraryBranchRepository = libraryBranchRepository;
        this.notificationService = notificationService;
        this.logger = logger;
    }

    // Checkout a copy of the book from a library's branch
    public void checkoutBook(final String patronId, final String bookCopyId, final String branchCode) {
        final Patron patron = patronRepository.getById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        final LibraryBranch libraryBranch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));

        if (patron.getBookCheckouts().size() >= MAX_CHECKOUTS) {
            throw new PatronMaxCheckoutsException(patronId, MAX_CHECKOUTS);
        }

        final BookCopy bookCopy = bookCopyRepository.getById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));

        if (patron.isBookCopyCheckedOut(bookCopy) || patron.isBookCheckedOut(bookCopy.getBook())) {
            throw new BookCopyCheckedOutException(patronId);
        }

        if (!bookCopy.isAvailable() || !bookCopy.getBranch().getId().equals(libraryBranch.getId())) {
            throw new BookCopyUnavailableException(bookCopyId);
        }

        bookCheckoutCount++;
        final BookCheckout bookCheckout = new BookCheckout(EntityCode.BOOK_CHECKOUT.getCode() + bookCheckoutCount,
                patron, bookCopy,
                LocalDate.now());
        bookCheckoutRepository.add(bookCheckout);

        bookCopy.setAvailable(false);
        patron.addBookCheckout(bookCheckout);

        bookCopyRepository.update(bookCopy);
        patronRepository.update(patron);

        final Optional<BookReservation> optionalBookReservation = bookReservationRepository.getByBookAndPatron(patron,
                bookCopy.getBook());
        if (optionalBookReservation.isPresent()) {
            final BookReservation bookReservation = optionalBookReservation.get();
            bookReservation.setStatus(BookReservationStatus.COMPLETED);
            bookReservationRepository.update(bookReservation);
        }

        logger.consoleOutput("Book checked out with checkout Id: " + bookCheckout.getId());
    }

    // Return the book copy
    public void returnBook(final String checkoutId) {
        final BookCheckout bookCheckout = bookCheckoutRepository.getById(checkoutId)
                .orElseThrow(() -> new BookCheckoutNotFoundException(checkoutId));

        bookCheckout.setReturnDate(LocalDate.now());
        final BookCopy bookCopy = bookCheckout.getBookCopy();
        bookCopy.setAvailable(true);

        bookCheckoutRepository.update(bookCheckout);
        bookCopyRepository.update(bookCopy);

        logger.info("Book returned with checkout Id: " + bookCheckout.getId());

        final List<BookReservation> bookReservations = bookReservationRepository.getByBookAndStatus(bookCopy.getBook(),
                BookReservationStatus.WAITING);

        if (!bookReservations.isEmpty()) {
            final BookReservation bookReservation = bookReservations.getFirst();
            notificationService.sendBookAvailableNotification(bookReservation.getPatron(), bookReservation.getBook());
        }
    }

    // Renew a book copy checkout
    public void renewBook(final String checkoutId) {
        final BookCheckout bookCheckout = bookCheckoutRepository.getById(checkoutId)
                .orElseThrow(() -> new BookCheckoutNotFoundException(checkoutId));
        if (bookCheckout.getDueDate().isBefore(LocalDate.now())) {
            throw new BookCopyOverdueException(bookCheckout.getBookCopy().getId());
        }

        if (bookCheckout.getRenewalCount() >= MAX_RENEWALS) {
            throw new BookCheckoutMaxRenewalsException(checkoutId, MAX_RENEWALS);
        }

        bookCheckout.setDueDate(bookCheckout.getDueDate().plusDays(15));
        bookCheckout.setRenewalCount(bookCheckout.getRenewalCount() + 1);

        bookCheckoutRepository.update(bookCheckout);
        logger.info("Book renewed with checkout Id: " + bookCheckout.getId());
    }
}