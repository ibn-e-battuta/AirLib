package repository.contract;

import java.util.List;
import java.util.Optional;

import model.Book;
import model.BookReservation;
import model.BookReservationStatus;
import model.Patron;
import repository.contract.base.IBaseRepository;

public interface IBookReservationRepository extends IBaseRepository<BookReservation, String> {
    List<BookReservation> getByBookAndStatus(final Book book, final BookReservationStatus bookReservationStatus);

    Optional<BookReservation> getByBookAndPatron(final Patron patron, final Book book);
}