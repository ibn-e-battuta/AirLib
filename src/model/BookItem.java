package model;

public class BookItem {
    private final String id;
    private Book book;
    private Branch branch;
    private boolean isAvailable;

    public BookItem(String id, Book book, Branch branch) {
        this.id = id;
        this.book = book;
        this.branch = branch;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
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
