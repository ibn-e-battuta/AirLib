package model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private final String id;
    private String name;
    private String address;
    private final List<BookItem> bookItems;

    public Branch(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookItems = new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
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

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public void addBookItem(BookItem bookItem) {
        bookItems.add(bookItem);
    }

    public void removeBookItem(BookItem bookItem) {
        bookItems.remove(bookItem);
    }

    @Override
    public String toString() {
        return "Branch [ " +
                "Id='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Address='" + address + '\'' +
                " ]";
    }
}
