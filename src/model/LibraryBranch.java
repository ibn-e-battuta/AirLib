package model;

import model.base.Deletable;

import java.util.ArrayList;
import java.util.List;

public class LibraryBranch extends Deletable {
    private String name;
    private String address;
    private final List<BookCopy> bookCopies;

    public LibraryBranch(String code, String name, String address) {
        super(code);
        this.name = name;
        this.address = address;
        this.bookCopies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BookCopy> getBookItems() {
        return bookCopies;
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(bookCopy);
    }

    public void removeBookCopy(BookCopy bookCopy) {
        bookCopies.remove(bookCopy);
    }
}
