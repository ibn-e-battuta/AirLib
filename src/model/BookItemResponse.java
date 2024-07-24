package model;

public class BookItemResponse {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String branchName;
    private boolean isAvailable;

    public BookItemResponse(String id, String title, String author, String isbn, String branchName, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.branchName = branchName;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "BookItem [ " +
                "ID='" + id + '\'' +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", ISBN='" + isbn + '\'' +
                ", Branch='" + branchName + '\'' +
                ", Available='" + isAvailable + '\'' +
                " ]";
    }
}
