package model;

import java.time.LocalDate;

public class Reservation {
    private final String id;
    private final Patron patron;
    private final BookItem bookItem;
    private final LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int renewalCount;

    public Reservation(String id, Patron patron, BookItem bookItem, LocalDate issueDate) {
        this.id = id;
        this.patron = patron;
        this.bookItem = bookItem;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(15);
        this.renewalCount = 0;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public Patron getPatron() {
        return patron;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(int renewalCount) {
        this.renewalCount = renewalCount;
    }
}
