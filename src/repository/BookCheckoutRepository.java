package repository;

import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookCheckoutRepository {
    private final Map<String, BookCheckout> checkouts = new HashMap<>();

    public void addCheckout(BookCheckout bookCheckout) {
        checkouts.put(bookCheckout.getId(), bookCheckout);
    }

    public void updateCheckout(BookCheckout bookCheckout) {
        checkouts.put(bookCheckout.getId(), bookCheckout);
    }

    public Optional<BookCheckout> getCheckout(String reservationId) {
        return Optional.ofNullable(checkouts.get(reservationId));
    }

    public List<BookCheckout> getActiveCheckoutsForPatron(Patron patron) {
        return checkouts.values().stream()
                .filter(r -> r.getPatron().equals(patron) && r.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public List<BookCheckout> getOverdueCheckouts(String patronId) {
        LocalDate today = LocalDate.now();
        return checkouts.values().stream()
                .filter(r -> r.getPatron().getId().equals(patronId) && r.getDueDate().isBefore(today) && r.getReturnDate() == null)
                .toList();
    }

    public List<BookCheckout> getCheckoutsForPatron(Patron patron) {
        return checkouts.values().stream()
                .filter(r -> r.getPatron().equals(patron))
                .sorted(Comparator.comparing(BookCheckout::getIssueDate).reversed())
                .toList();
    }





    public List<BookCheckout> getReservationsWithDueDate(LocalDate dueDate) {
        return checkouts.values().stream()
                .filter(r -> r.getDueDate().equals(dueDate) && r.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public void removeReservation(String reservationId) {
        checkouts.remove(reservationId);
    }



    public int getActiveReservationCount(Patron patron) {
        return (int) checkouts.values().stream()
                .filter(r -> r.getPatron().equals(patron) && r.getReturnDate() == null)
                .count();
    }

    public boolean hasActiveReservation(BookCopy bookCopy) {
        return checkouts.values().stream()
                .anyMatch(r -> r.getBookItem().equals(bookCopy) && r.getReturnDate() == null);
    }



    public List<Book> getMostPopularBooks(int limit) {
        Map<Book, Long> bookFrequency = checkouts.values().stream()
                .map(r -> r.getBookItem().getBook())
                .collect(Collectors.groupingBy(book -> book, Collectors.counting()));

        return bookFrequency.entrySet().stream()
                .sorted(Map.Entry.<Book, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public boolean isBookCheckedOutByPatron(Patron patron, Book book) {
        return checkouts.values().stream().anyMatch(c -> c.getPatron().equals(patron) && c.getBookItem().getBook().equals(book) && c.getReturnDate() == null);
    }
}