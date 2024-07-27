package response;

import model.Book;

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

    public BookResponse(Book book) {
        this.isbn = book.getId();
        this.title = book.getTitle();
        this.authors = String.join(", ", book.getAuthors());
        this.genres = String.join(", ", book.getGenres());
        this.year = book.getPublicationYear();
    }


    @Override
    public String toString() {
        return "Book [ " +
                "ISBN='" + isbn + '\'' +
                ", Title='" + title + '\'' +
                ", Author(s)='" + authors + '\'' +
                ", Genre(s)='" + genres + '\'' +
                ", Year='" + year + '\'' +
                " ]";
    }
}