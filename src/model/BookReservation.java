package model;

import java.time.LocalDate;

public class BookReservation {
    private final String id;
    private final Patron patron;
    private final Book book;
    private LocalDate reservationDate;
    private ReservationStatus status;

    public BookReservation(String id, Patron patron, Book book) {
        this.id = id;
        this.patron = patron;
        this.book = book;
        this.reservationDate = LocalDate.now();
        this.status = ReservationStatus.WAITING;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public Patron getPatron() {
        return patron;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
