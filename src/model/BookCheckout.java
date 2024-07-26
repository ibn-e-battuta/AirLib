package model;

import model.base.BaseModel;

import java.time.LocalDate;

public class BookCheckout extends BaseModel {
    private final Patron patron;
    private final BookCopy bookCopy;
    private final LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int renewalCount;

    public BookCheckout(String id, Patron patron, BookCopy bookCopy, LocalDate issueDate) {
        super(id);
        this.patron = patron;
        this.bookCopy = bookCopy;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(15);
        this.renewalCount = 0;
    }

    public Patron getPatron() {
        return patron;
    }

    public BookCopy getBookItem() {
        return bookCopy;
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
