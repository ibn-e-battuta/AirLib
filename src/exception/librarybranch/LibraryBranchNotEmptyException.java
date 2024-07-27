package exception.librarybranch;

import exception.category.InvalidOperationException;
import model.LibraryBranch;

public class LibraryBranchNotEmptyException extends InvalidOperationException {
    public LibraryBranchNotEmptyException(final String branchCode, final int numOfBooks) {
        super(LibraryBranch.class.getSimpleName(), branchCode,
                "cannot be removed, as it contains " + numOfBooks + " book copies");
    }
}