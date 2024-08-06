package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.BookCopy;
import model.LibraryBranch;
import repository.contract.IBookCopyRepository;

public class BookCopyRepository implements IBookCopyRepository {
    private final Map<String, BookCopy> bookCopies = new HashMap<>();

    public Optional<BookCopy> getById(String bookCopyId) {
        return bookCopies.values().stream()
                .filter(bc -> bc.getId().equals(bookCopyId) && !bc.isDeleted()).findAny();
    }

    public void add(BookCopy bookCopy) {
        bookCopies.put(bookCopy.getId(), bookCopy);
    }

    public void update(BookCopy bookCopy) {
        bookCopies.put(bookCopy.getId(), bookCopy);
    }

    public boolean isBookInUse(String isbn) {
        return bookCopies.values().stream()
                .anyMatch(bc -> bc.getBook().getId().equals(isbn) && !bc.isAvailable() && !bc.isDeleted());
    }

    public void removeAll(String isbn) {
        bookCopies.values().stream()
                .filter(bc -> bc.getBook().getId().equals(isbn))
                .forEach(bc -> bc.setDeleted(true));
    }

    public List<BookCopy> getAllByAvailability(final boolean isAvailable) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.isAvailable() == isAvailable)
                .toList();
    }

    public List<BookCopy> getAllByBranchAndAvailability(final LibraryBranch branch, final boolean isAvailable) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.isAvailable() == isAvailable && bc.getBranch().equals(branch))
                .toList();
    }

    public List<BookCopy> getAllByIsbn(final String isbn) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBook().getId().equals(isbn)).toList();
    }

    public List<BookCopy> getAllByBranchCode(String branchCode) {
        return bookCopies.values().stream()
                .filter(bc -> !bc.isDeleted() && bc.getBranch().getId().equals(branchCode))
                .toList();
    }
}