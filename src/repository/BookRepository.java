package repository;

import model.Book;
import model.BookItem;
import model.Branch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import exception.BookNotFoundException;

public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, BookItem> bookItems = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addBookItem(BookItem bookItem) {
        bookItems.put(bookItem.getId(), bookItem);
    }

    public void updateBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public boolean isBookInUse(String isbn) throws BookNotFoundException {
        if (!books.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }

        return bookItems.values().stream()
                .anyMatch(item -> item.getBook().getIsbn().equals(isbn) && !item.isAvailable());
    }

    public void removeBook(String isbn) {
        books.remove(isbn);
        bookItems.values().removeIf(item -> item.getBook().getIsbn().equals(isbn));
    }

    public Optional<Book> getBook(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public List<Book> searchBooks(String query, String searchBy) {
        String lowercaseQuery = query.toLowerCase();
        return books.values().stream()
                .filter(book -> {
                    switch (searchBy.toLowerCase()) {
                        case "title":
                            return book.getTitle().toLowerCase().contains(lowercaseQuery);
                        case "author":
                            return book.getAuthor().toLowerCase().contains(lowercaseQuery);
                        case "subject":
                            return book.getSubject().toLowerCase().contains(lowercaseQuery);
                        case "publication_date":
                            return book.getPublicationYear().toString().contains(lowercaseQuery);
                        case "isbn":
                            return book.getIsbn().toLowerCase().contains(lowercaseQuery);
                        default:
                            return book.getTitle().toLowerCase().contains(lowercaseQuery)
                                    || book.getAuthor().toLowerCase().contains(lowercaseQuery)
                                    || book.getSubject().toLowerCase().contains(lowercaseQuery)
                                    || book.getIsbn().toLowerCase().contains(lowercaseQuery)
                                    || book.getPublicationYear().toString().contains(lowercaseQuery);
                    }
                })
                .toList();
    }

    public List<BookItem> getBookItems(String isbn) {
        return bookItems.values().stream()
                .filter(item -> item.getBook().getIsbn().equals(isbn))
                .toList();
    }

    public Optional<BookItem> getBookItem(String bookItemId) {
        return Optional.ofNullable(bookItems.get(bookItemId));
    }

    public int getTotalCopies(String isbn) {
        return (int) bookItems.values().stream()
                .filter(item -> item.getBook().getIsbn().equals(isbn))
                .count();
    }

    public int getLentCopies(String isbn) {
        return (int) bookItems.values().stream()
                .filter(item -> item.getBook().getIsbn().equals(isbn) && !item.isAvailable())
                .count();
    }

    public List<BookItem> getAvailableBookItems(String isbn, String branchId) {
        return bookItems.values().stream()
                .filter(item -> item.getBook().getIsbn().equals(isbn)
                        && item.getBranch().getId().equals(branchId)
                        && item.isAvailable())
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getBookCopiesByBranch(String isbn) {
        return bookItems.values().stream()
                .filter(item -> item.getBook().getIsbn().equals(isbn))
                .collect(Collectors.groupingBy(
                        item -> item.getBranch().getId(),
                        Collectors.summingInt(item -> 1)));
    }

    public List<BookItem> getBookItemsByBranch(String branchId) {
        return bookItems.values().stream()
                .filter(item -> item.getBranch().getId().equals(branchId))
                .toList();
    }

    public void updateBookItem(BookItem bookItem) {
        bookItems.put(bookItem.getId(), bookItem);
    }

    public void updateBranchInBookItems(Branch branch) {
        bookItems.values().stream()
                .filter(item -> item.getBranch().getId().equals(branch.getId()))
                .forEach(item -> item.setBranch(branch));
    }
}
