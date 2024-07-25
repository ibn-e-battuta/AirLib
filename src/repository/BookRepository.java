package repository;

import model.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void updateBook(Book book) {
        books.put(book.getId(), book);
    }

    public void removeBook(String isbn) {
        var book = books.get(isbn);
        book.setDeleted(true);
    }

    public Optional<Book> getBook(String isbn) {
        return books.values().stream().filter(b -> b.getId().equals(isbn) && !b.isDeleted()).findAny();
    }

    public List<Book> findByGenre(String genre) {
        return books.values().stream()
                .filter(b -> !b.isDeleted() && b.getGenres().contains(genre))
                .toList();
    }

    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(b -> !b.isDeleted() && b.getAuthors().contains(author))
                .toList();
    }

    public List<Book> findByTitleOrGenreOrAuthor(String query) {
        String lowercaseQuery = query.toLowerCase();
        return books.values().stream()
                .filter(b -> !b.isDeleted() && (b.getTitle().toLowerCase().contains(lowercaseQuery) ||
                        b.getGenres().stream().anyMatch(genre -> genre.toLowerCase().contains(lowercaseQuery)) ||
                        b.getAuthors().stream().anyMatch(author -> author.toLowerCase().contains(lowercaseQuery))))
                .toList();
    }

    public List<Book> searchBooks(String query, String searchBy) {
        String lowercaseQuery = query.toLowerCase();
        return books.values().stream()
                .filter(b -> !b.isDeleted() && (switch (searchBy.toLowerCase()) {
                    case "title" -> b.getTitle().toLowerCase().contains(lowercaseQuery);
                    case "author" ->
                        b.getAuthors().stream().anyMatch(author -> author.toLowerCase().contains(lowercaseQuery));
                    case "genre" ->
                        b.getGenres().stream().anyMatch(genre -> genre.toLowerCase().contains(lowercaseQuery));
                    case "isbn" -> b.getId().toLowerCase().contains(lowercaseQuery);
                    case "publication_year" -> b.getPublicationYear().toString().equals(lowercaseQuery);
                    default -> b.getTitle().toLowerCase().contains(lowercaseQuery) ||
                            b.getAuthors().stream().anyMatch(author -> author.toLowerCase().contains(lowercaseQuery))
                            ||
                            b.getGenres().stream().anyMatch(genre -> genre.toLowerCase().contains(lowercaseQuery)) ||
                            b.getId().toLowerCase().contains(lowercaseQuery);
                }))
                .toList();
    }
}
