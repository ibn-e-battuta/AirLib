package model;

import java.util.List;

import model.base.Deletable;

public class Book extends Deletable {
    private String title;
    private final List<String> authors;
    private final List<String> genres;
    private int publicationYear;

    public Book(String isbn, String title, List<String> authors, List<String> genres, int publicationYear) {
        super(isbn);
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors.clear();
        this.authors.addAll(authors);
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres.clear();
        this.genres.addAll(genres);
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
}