package model;

import java.util.ArrayList;
import java.util.List;

import model.base.BaseModel;

public class Patron extends BaseModel {
    private String name;
    private String email;
    private final List<BookCheckout> bookCheckouts;
    private final List<String> preferences;

    public Patron(String id, String name, String email, List<String> preferences) {
        super(id);
        this.name = name;
        this.email = email;
        this.bookCheckouts = new ArrayList<>();
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BookCheckout> getBookCheckouts() {
        return bookCheckouts;
    }

    public void addBookCheckout(BookCheckout bookCheckout) {
        bookCheckouts.add(bookCheckout);
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences.clear();
        this.preferences.addAll(preferences);
    }

    public boolean isBookCheckedOut(Book book) {
        return bookCheckouts.stream()
                .anyMatch(bc -> bc.getBookCopy().getBook().equals(book) && bc.getReturnDate() == null);
    }

    public boolean isBookCopyCheckedOut(BookCopy bookCopy) {
        return bookCheckouts.stream()
                .anyMatch(bc -> bc.getBookCopy().equals(bookCopy) && bc.getReturnDate() == null);
    }

    public List<BookCheckout> getActiveBookCheckouts() {
        return bookCheckouts.stream().filter(bc -> bc.getReturnDate() == null).toList();
    }
}