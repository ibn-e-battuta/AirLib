package response;

import model.Patron;

public class PatronResponse {
    private final String name;
    private final String email;
    private final int booksBorrowed;

    public PatronResponse(String name, String email, int booksBorrowed) {
        this.name = name;
        this.email = email;
        this.booksBorrowed = booksBorrowed;
    }

    public PatronResponse(Patron patron) {
        this.name = patron.getName();
        this.email = patron.getEmail();
        this.booksBorrowed = patron.getBookCheckouts().size();
    }

    @Override
    public String toString() {
        return "Patron [ " +
                "Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", BooksBorrowed=" + booksBorrowed +
                " ]";
    }
}
