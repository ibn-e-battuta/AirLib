package model;

import model.base.Deletable;

public class BookCopy extends Deletable {
    private Book book;
    private LibraryBranch branch;
    private boolean isAvailable;

    public BookCopy(String id, Book book, LibraryBranch branch) {
        super(id);
        this.book = book;
        this.branch = branch;
        this.isAvailable = true;
    }

    public Book getBook() {
        return book;
    }

    public LibraryBranch getBranch() {
        return branch;
    }

    public void setLibraryBranch(LibraryBranch branch) {
        this.branch = branch;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}