package repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Book;
import model.BookCheckout;
import model.Patron;
import repository.contract.IBookCheckoutRepository;

public class BookCheckoutRepository implements IBookCheckoutRepository {
    private final Map<String, BookCheckout> checkouts = new HashMap<>();

    public void add(final BookCheckout bookCheckout) {
        checkouts.put(bookCheckout.getId(), bookCheckout);
    }

    public void update(final BookCheckout bookCheckout) {
        checkouts.put(bookCheckout.getId(), bookCheckout);
    }

    public Optional<BookCheckout> getById(final String reservationId) {
        return Optional.ofNullable(checkouts.get(reservationId));
    }

    public List<BookCheckout> getByPatron(final Patron patron) {
        return checkouts.values().stream()
                .filter(r -> r.getPatron().equals(patron))
                .sorted(Comparator.comparing(BookCheckout::getIssueDate).reversed())
                .toList();
    }

    public List<Book> getMostPopularBooks(final int limit) {
        Map<Book, Long> bookFrequency = checkouts.values().stream()
                .map(r -> r.getBookCopy().getBook())
                .collect(Collectors.groupingBy(book -> book, Collectors.counting()));

        return bookFrequency.entrySet().stream()
                .sorted(Map.Entry.<Book, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}