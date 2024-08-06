package repository.contract;

import java.util.List;

import model.BookCopy;
import model.LibraryBranch;
import repository.contract.base.IBaseRepository;

public interface IBookCopyRepository extends IBaseRepository<BookCopy, String> {
    boolean isBookInUse(final String isbn);

    void removeAll(final String isbn);

    List<BookCopy> getAllByBranchCode(final String branchCode);

    List<BookCopy> getAllByAvailability(final boolean isAvailable);

    List<BookCopy> getAllByBranchAndAvailability(final LibraryBranch branch, final boolean isAvailable);

    List<BookCopy> getAllByIsbn(final String isbn);
}