package model.response;

import java.util.List;

public class BookResponse {
    private final String isbn;
    private final String title;
    private final String authors;
    private final String genres;
    private final int year;

    public BookResponse(String isbn, String title, List<String> authors, List<String> genres, int year) {
        this.isbn = isbn;
        this.title = title;
        this.authors = String.join(", ", authors);
        this.genres = String.join(", ", genres);        
        this.year = year;
    }

    @Override
    public String toString() {
        return "[ " +
                "ISBN='" + isbn + '\'' +
                ", Title='" + title + '\'' +
                ", Author(s)='" + authors + '\'' +
                ", Genre(s)='" + genres + '\'' +
                ", Year='" + year + '\'' +
                " ]";
    }
}