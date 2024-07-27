package model;

import model.base.BaseModel;

import java.time.LocalDate;

public class BookReservation extends BaseModel {
    private final Patron patron;
    private final Book book;
    private final LocalDate reservationDate;
    private BookReservationStatus status;

    public BookReservation(String id, Patron patron, Book book) {
        super(id);
        this.patron = patron;
        this.book = book;
        this.reservationDate = LocalDate.now();
        this.status = BookReservationStatus.WAITING;
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

    public BookReservationStatus getStatus() {
        return status;
    }

    public void setStatus(BookReservationStatus status) {
        this.status = status;
    }
}