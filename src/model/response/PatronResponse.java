package model.response;

public class PatronResponse {
    private final String name;
    private final String email;
    private final int booksBorrowed;

    public PatronResponse(String name, String email, int booksBorrowed) {
        this.name = name;
        this.email = email;
        this.booksBorrowed = booksBorrowed;
    }

    @Override
    public String toString() {
        return "[ " +
                "Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", BooksBorrowed=" + booksBorrowed +
                " ]";
    }
}
