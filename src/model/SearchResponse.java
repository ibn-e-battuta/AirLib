package model;

public class SearchResponse {
    private String title;
    private String author;
    private String isbn;
    private int year;

    public SearchResponse(String title, String author, String isbn, int year) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book [ " +
                "Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", ISBN='" + isbn + '\'' +
                ", Year='" + year + '\'' +
                " ]";
    }
}