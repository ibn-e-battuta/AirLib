package repository;

import model.BookCopy;
import model.LibraryBranch;

import java.util.*;

public class BookCopyRepository {
    private final Map<String, BookCopy> bookCopies = new HashMap<>();

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.put(bookCopy.getId(), bookCopy);
    }

    public boolean isBookInUse(String isbn) {
        return bookCopies.values().stream()
                .anyMatch(bc -> bc.getBook().getId().equals(isbn) && !bc.isAvailable() && !bc.isDeleted());
    }

    public void removeBookCopies(String isbn) {
        bookCopies.values().stream()
                .filter(bc -> bc.getBook().getId().equals(isbn))
                .forEach(bc -> bc.setDeleted(true));
    }

    public Optional<BookCopy> getBookCopy(String bookCopyId) {
        return bookCopies.values().stream()
                .filter(bc -> bc.getId().equals(bookCopyId) && !bc.isDeleted()).findAny();
    }

    public void updateBookCopy(BookCopy bookCopy) {
        bookCopies.put(bookCopy.getId(), bookCopy);
    }

    public List<BookCopy> getAvailableBookCopies() {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.isAvailable())
                .toList();
    }

    public List<BookCopy> getAvailableBookCopiesForBranch(LibraryBranch branch) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.isAvailable() && bc.getBranch().equals(branch))
                .toList();
    }

    public List<BookCopy> getBorrowedBookCopies() {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && !bc.isAvailable())
                .toList();
    }

    public List<BookCopy> getBorrowedBookCopiesForBranch(LibraryBranch branch) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && !bc.isAvailable() && bc.getBranch().equals(branch))
                .toList();
    }

    public int getAvailableBookCopiesCount(String isbn) {
        return (int) bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBook().getId().equals(isbn) && bc.isAvailable())
                .count();
    }

    public int getBorrowedBookItemCount(String isbn) {
        return (int) bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBook().getId().equals(isbn) && !bc.isAvailable())
                .count();
    }

    public int getTotalBookItemCount(String isbn) {
        return (int) bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBook().getId().equals(isbn))
                .count();
    }

    public List<BookCopy> getBookItemsByBranch(String branchId) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBranch().getId().equals(branchId))
                .toList();
    }
}
