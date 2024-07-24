package service;

import model.Book;
import model.Patron;
import repository.BookRepository;
import repository.ReservationRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public RecommendationService(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getRecommendations(Patron patron) {
        List<Book> borrowedBooks = getBorrowedBooks(patron);
        Map<String, Double> subjectScores = calculateSubjectScores(borrowedBooks);
        Map<String, Double> authorScores = calculateAuthorScores(borrowedBooks);
        Set<Book> recommendedBooks = new HashSet<>();

        // Get recommendations based on subject preferences
        recommendedBooks.addAll(getSubjectBasedRecommendations(subjectScores, borrowedBooks));

        // Get recommendations based on author preferences
        recommendedBooks.addAll(getAuthorBasedRecommendations(authorScores, borrowedBooks));

        // Get recommendations based on patron's explicit preferences
        recommendedBooks.addAll(getPreferenceBasedRecommendations(patron, borrowedBooks));

        // Get popular books that the patron hasn't read
        recommendedBooks.addAll(getPopularBooks(borrowedBooks));

        // Get recent books in patron's favorite subjects
        recommendedBooks.addAll(getRecentBooks(subjectScores, borrowedBooks));

        return new ArrayList<>(recommendedBooks);
    }

    private List<Book> getBorrowedBooks(Patron patron) {
        return reservationRepository.getReservationsForPatron(patron).stream()
                .map(reservation -> reservation.getBookItem().getBook())
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<String, Double> calculateSubjectScores(List<Book> borrowedBooks) {
        Map<String, Double> subjectScores = new HashMap<>();
        LocalDate now = LocalDate.now();

        for (Book book : borrowedBooks) {
            double score = 1.0;
            // Give higher weight to recently borrowed books
            if (book.getPublicationYear() != null) {
                long daysSincePublication = book.getPublicationYear();
                score *= Math.exp(-daysSincePublication / 365.0); // Decay factor
            }
            subjectScores.merge(book.getSubject(), score, Double::sum);
        }

        return normalizeScores(subjectScores);
    }

    private Map<String, Double> calculateAuthorScores(List<Book> borrowedBooks) {
        Map<String, Double> authorScores = new HashMap<>();
        for (Book book : borrowedBooks) {
            authorScores.merge(book.getAuthor(), 1.0, Double::sum);
        }
        return normalizeScores(authorScores);
    }

    private Map<String, Double> normalizeScores(Map<String, Double> scores) {
        double max = scores.values().stream().max(Double::compare).orElse(1.0);
        scores.replaceAll((k, v) -> v / max);
        return scores;
    }

    private List<Book> getSubjectBasedRecommendations(Map<String, Double> subjectScores, List<Book> borrowedBooks) {
        return subjectScores.entrySet().stream()
                .flatMap(entry -> bookRepository.searchBooks(entry.getKey(), "subject").stream()
                        .filter(book -> !borrowedBooks.contains(book))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Book> getAuthorBasedRecommendations(Map<String, Double> authorScores, List<Book> borrowedBooks) {
        return authorScores.entrySet().stream()
                .flatMap(entry -> bookRepository.searchBooks(entry.getKey(), "author").stream()
                        .filter(book -> !borrowedBooks.contains(book))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Book> getPreferenceBasedRecommendations(Patron patron, List<Book> borrowedBooks) {
        return patron.getPreferences().stream()
                .flatMap(preference -> bookRepository.searchBooks(preference, "").stream())
                .filter(book -> !borrowedBooks.contains(book))
                .distinct()
                .limit(3)
                .collect(Collectors.toList());
    }

    private List<Book> getPopularBooks(List<Book> borrowedBooks) {
        return reservationRepository.getMostPopularBooks(10).stream()
                .filter(book -> !borrowedBooks.contains(book))
                .limit(2)
                .collect(Collectors.toList());
    }

    private List<Book> getRecentBooks(Map<String, Double> subjectScores, List<Book> borrowedBooks) {
        /*
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return subjectScores.entrySet().stream()
                .flatMap(entry -> bookRepository.searchBooks(entry.getKey(), "subject").stream()
                        .filter(book -> !borrowedBooks.contains(book) && book.getPublicationYear().isAfter(sixMonthsAgo))
                        .map(book -> new AbstractMap.SimpleEntry<>(book, entry.getValue())))
                .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
*/
                return null;
    }
}
