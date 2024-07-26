package repository;

import model.Book;
import model.BookReservation;
import model.Patron;
import model.ReservationStatus;

import java.util.*;
import java.util.stream.Collectors;

public class BookReservationRepository {
    private final Map<String, BookReservation> bookReservations = new HashMap<>();

    public void addReservation(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public void updateReservation(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public List<BookReservation> getWaitingReservations(Book book) {
        return bookReservations.values().stream()
                .filter(r -> r.getBook().equals(book) && r.getStatus() == ReservationStatus.WAITING)
                .sorted(Comparator.comparing(BookReservation::getReservationDate))
                .toList();
    }

    public List<BookReservation> getAllActiveBookReservations() {
        return bookReservations.values().stream()
                .filter(br -> br.getStatus() == ReservationStatus.WAITING)
                .collect(Collectors.toList());
    }

    public void cancelBookReservation(String bookReservationId) {
        var reservation = bookReservations.get(bookReservationId);
        reservation.setStatus(ReservationStatus.CANCELLED);
    }

    public Optional<BookReservation> getBookReservation(String bookReservationId) {
        return bookReservations.values().stream().filter(b -> b.getId().equals(bookReservationId)).findAny();
    }

    public Optional<BookReservation> getBookReservationByBookAndPatron(Patron patron, Book book) {
        return bookReservations.values().stream()
                .filter(br -> br.getPatron().equals(patron) && br.getBook().equals(book) && br.getStatus() == ReservationStatus.WAITING).findAny();
    }
}
