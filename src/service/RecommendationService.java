package service;

import exception.PatronNotFoundException;
import model.Book;
import model.Patron;
import model.response.BookResponse;
import repository.BookCheckoutRepository;
import repository.BookRepository;
import repository.PatronRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    private final BookCheckoutRepository bookCheckoutRepository;
    private final BookRepository bookRepository;
    private final PatronRepository _patronRepository;

    public RecommendationService(BookCheckoutRepository bookCheckoutRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.bookCheckoutRepository = bookCheckoutRepository;
        this.bookRepository = bookRepository;
        _patronRepository = patronRepository;
    }

    public List<BookResponse> getRecommendations(String patronId) throws PatronNotFoundException {
        var patron = _patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));
        List<Book> borrowedBooks = getBorrowedBooks(patron);
        Map<String, Double> genreScores = calculateGenreScores(borrowedBooks);
        Map<String, Double> authorScores = calculateAuthorScores(borrowedBooks);
        Set<Book> recommendedBooks = new HashSet<>();

        recommendedBooks.addAll(getGenreBasedRecommendations(genreScores, borrowedBooks));
        recommendedBooks.addAll(getAuthorBasedRecommendations(authorScores, borrowedBooks));
        recommendedBooks.addAll(getPreferenceBasedRecommendations(patron, borrowedBooks));
        recommendedBooks.addAll(getPopularBooks(borrowedBooks));
        recommendedBooks.addAll(getRecentBooks(genreScores, borrowedBooks));

        return recommendedBooks.stream().map(b -> new BookResponse(b.getId(), b.getTitle(), b.getAuthors(), b.getGenres(), b.getPublicationYear())).toList();
    }

    private List<Book> getBorrowedBooks(Patron patron) {
        return bookCheckoutRepository.getCheckoutsForPatron(patron).stream()
                .map(reservation -> reservation.getBookItem().getBook())
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<String, Double> calculateGenreScores(List<Book> borrowedBooks) {
        Map<String, Double> genreScores = new HashMap<>();
        borrowedBooks.forEach(book ->
                book.getGenres().forEach(genre ->
                        genreScores.merge(genre, 1.0, Double::sum)
                )
        );
        return normalizeScores(genreScores);
    }

    private Map<String, Double> calculateAuthorScores(List<Book> borrowedBooks) {
        Map<String, Double> authorScores = new HashMap<>();
        borrowedBooks.forEach(book ->
                book.getAuthors().forEach(author ->
                        authorScores.merge(author, 1.0, Double::sum)
                )
        );
        return normalizeScores(authorScores);
    }

    private Map<String, Double> normalizeScores(Map<String, Double> scores) {
        double max = scores.values().stream().max(Double::compare).orElse(1.0);
        scores.replaceAll((k, v) -> v / max);
        return scores;
    }

    private List<Book> getGenreBasedRecommendations(Map<String, Double> genreScores, List<Book> borrowedBooks) {
        return genreScores.entrySet().stream()
                .flatMap(entry -> bookRepository.findByGenre(entry.getKey()).stream()
                        .filter(book -> !borrowedBooks.contains(book))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Book> getAuthorBasedRecommendations(Map<String, Double> authorScores, List<Book> borrowedBooks) {
        return authorScores.entrySet().stream()
                .flatMap(entry -> bookRepository.findByAuthor(entry.getKey()).stream()
                        .filter(book -> !borrowedBooks.contains(book))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Book> getPreferenceBasedRecommendations(Patron patron, List<Book> borrowedBooks) {
        return patron.getPreferences().stream()
                .flatMap(preference -> bookRepository.findByTitleOrGenreOrAuthor(preference).stream())
                .filter(book -> !borrowedBooks.contains(book))
                .distinct()
                .limit(3)
                .collect(Collectors.toList());
    }

    private List<Book> getPopularBooks(List<Book> borrowedBooks) {
        return bookCheckoutRepository.getMostPopularBooks(10).stream()
                .filter(book -> !borrowedBooks.contains(book))
                .limit(2)
                .collect(Collectors.toList());
    }

    private List<Book> getRecentBooks(Map<String, Double> genreScores, List<Book> borrowedBooks) {
        int currentYear = LocalDate.now().getYear();
        return genreScores.entrySet().stream()
                .flatMap(entry -> bookRepository.findByGenre(entry.getKey()).stream()
                        .filter(book -> !borrowedBooks.contains(book) && book.getPublicationYear() >= currentYear - 2)
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}