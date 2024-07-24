package repository;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import exception.ReservationNotFoundException;

public class ReservationRepository {
    private final Map<String, Reservation> reservations = new HashMap<>();
    private final Map<String, BookReservation> bookReservations = new HashMap<>();

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public void updateReservation(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public Optional<Reservation> getReservation(String reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    public List<Reservation> getActiveReservationsForPatron(Patron patron) {
        return reservations.values().stream()
                .filter(r -> r.getPatron().equals(patron) && r.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public List<Reservation> getOverdueReservations() {
        LocalDate today = LocalDate.now();
        return reservations.values().stream()
                .filter(r -> r.getDueDate().isBefore(today) && r.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsForPatron(Patron patron) {
        return reservations.values().stream()
                .filter(r -> r.getPatron().equals(patron))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsByBook(Book book) {
        return reservations.values().stream()
                .filter(r -> r.getBookItem().getBook().equals(book))
                .collect(Collectors.toList());
    }

    public void addBookReservation(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public void updateBookReservation(BookReservation bookReservation) {
        bookReservations.put(bookReservation.getId(), bookReservation);
    }

    public List<BookReservation> getWaitingReservations(Book book) {
        return bookReservations.values().stream()
                .filter(br -> br.getBook().equals(book) && br.getStatus() == ReservationStatus.WAITING)
                .sorted(Comparator.comparing(BookReservation::getReservationDate))
                .collect(Collectors.toList());
    }

    public List<BookReservation> getAllActiveBookReservations() {
        return bookReservations.values().stream()
                .filter(br -> br.getStatus() == ReservationStatus.WAITING)
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsWithDueDate(LocalDate dueDate) {
        return reservations.values().stream()
                .filter(r -> r.getDueDate().equals(dueDate) && r.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public void removeReservation(String reservationId) {
        reservations.remove(reservationId);
    }

    public void removeBookReservation(String bookReservationId) {
        bookReservations.remove(bookReservationId);
    }

    public int getActiveReservationCount(Patron patron) {
        return (int) reservations.values().stream()
                .filter(r -> r.getPatron().equals(patron) && r.getReturnDate() == null)
                .count();
    }

    public boolean hasActiveReservation(BookItem bookItem) {
        return reservations.values().stream()
                .anyMatch(r -> r.getBookItem().equals(bookItem) && r.getReturnDate() == null);
    }
}