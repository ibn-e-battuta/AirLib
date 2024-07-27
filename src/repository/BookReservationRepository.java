package repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Book;
import model.BookReservation;
import model.BookReservationStatus;
import model.Patron;
import repository.contract.IBookReservationRepository;

public class BookReservationRepository implements IBookReservationRepository {
    private final Map<String, BookReservation> bookReservations = new HashMap<>();

    public Optional<BookReservation> getById(String bookReservationId) {
        return bookReservations.values().stream().filter(b -> b.getId().equals(bookReservationId)).findAny();
    }

    public void add(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public void update(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public List<BookReservation> getByBookAndStatus(Book book, BookReservationStatus bookReservationStatus) {
        return bookReservations.values().stream()
                .filter(r -> r.getBook().equals(book) && r.getStatus() == bookReservationStatus)
                .sorted(Comparator.comparing(BookReservation::getReservationDate))
                .toList();
    }

    public Optional<BookReservation> getByBookAndPatron(Patron patron, Book book) {
        return bookReservations.values().stream()
                .filter(br -> br.getPatron().equals(patron) && br.getBook().equals(book)
                        && br.getStatus() == BookReservationStatus.WAITING)
                .findAny();
    }
}