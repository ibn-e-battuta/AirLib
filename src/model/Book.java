package model;

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private String subject;
    private Integer publicationYear;

    public Book(String isbn, String title, String author, String subject, Integer publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publicationYear = publicationYear;
    }

    // Getters and setters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
}
