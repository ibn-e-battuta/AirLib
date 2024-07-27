package service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exception.patron.PatronNotFoundException;
import model.Book;
import model.Patron;
import repository.contract.IBookCheckoutRepository;
import repository.contract.IBookRepository;
import repository.contract.IPatronRepository;
import response.BookResponse;

public class BookRecommendationService {
    private final IBookCheckoutRepository bookCheckoutRepository;
    private final IBookRepository bookRepository;
    private final IPatronRepository patronRepository;

    public BookRecommendationService(IBookCheckoutRepository bookCheckoutRepository, IBookRepository bookRepository,
            IPatronRepository patronRepository) {
        this.bookCheckoutRepository = bookCheckoutRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    public List<BookResponse> getBookRecommendations(final String patronId) {
        final Patron patron = patronRepository.getById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));
        final List<Book> borrowedBooks = getBorrowedBooks(patron);
        final Map<String, Double> genreScores = calculateGenreScores(borrowedBooks);
        final Map<String, Double> authorScores = calculateAuthorScores(borrowedBooks);
        final Set<Book> recommendedBooks = new HashSet<>();

        recommendedBooks.addAll(getGenreBasedRecommendations(genreScores, borrowedBooks));
        recommendedBooks.addAll(getAuthorBasedRecommendations(authorScores, borrowedBooks));
        recommendedBooks.addAll(getPreferenceBasedRecommendations(patron, borrowedBooks));
        recommendedBooks.addAll(getPopularBooks(borrowedBooks));
        recommendedBooks.addAll(getRecentBooks(genreScores, borrowedBooks));

        return recommendedBooks.stream().map(BookResponse::new).toList();
    }

    private List<Book> getBorrowedBooks(final Patron patron) {
        return bookCheckoutRepository.getByPatron(patron).stream()
                .map(reservation -> reservation.getBookCopy().getBook())
                .distinct()
                .toList();
    }

    private Map<String, Double> calculateGenreScores(List<Book> borrowedBooks) {
        Map<String, Double> genreScores = new HashMap<>();
        borrowedBooks.forEach(
                book -> book.getGenres().forEach(genre -> genreScores.merge(genre, 1.0, Double::sum)));
        return normalizeScores(genreScores);
    }

    private Map<String, Double> calculateAuthorScores(List<Book> borrowedBooks) {
        Map<String, Double> authorScores = new HashMap<>();
        borrowedBooks.forEach(book -> book.getAuthors()
                .forEach(author -> authorScores.merge(author, 1.0, Double::sum)));
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
                .toList();
    }

    private List<Book> getAuthorBasedRecommendations(Map<String, Double> authorScores, List<Book> borrowedBooks) {
        return authorScores.entrySet().stream()
                .flatMap(entry -> bookRepository.findByAuthor(entry.getKey()).stream()
                        .filter(book -> !borrowedBooks.contains(book))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<Book> getPreferenceBasedRecommendations(Patron patron, List<Book> borrowedBooks) {
        return patron.getPreferences().stream()
                .flatMap(preference -> bookRepository.findByTitleOrGenreOrAuthor(preference).stream())
                .filter(book -> !borrowedBooks.contains(book))
                .distinct()
                .limit(3)
                .toList();
    }

    private List<Book> getPopularBooks(List<Book> borrowedBooks) {
        return bookCheckoutRepository.getMostPopularBooks(10).stream()
                .filter(book -> !borrowedBooks.contains(book))
                .limit(2)
                .toList();
    }

    private List<Book> getRecentBooks(Map<String, Double> genreScores, List<Book> borrowedBooks) {
        int currentYear = LocalDate.now().getYear();
        return genreScores.entrySet().stream()
                .flatMap(entry -> bookRepository.findByGenre(entry.getKey()).stream()
                        .filter(book -> !borrowedBooks.contains(book)
                                && book.getPublicationYear() >= currentYear - 2)
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();
    }
}