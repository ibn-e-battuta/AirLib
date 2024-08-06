package exception.librarybranch;

import exception.category.NotFoundException;
import model.LibraryBranch;

public class LibraryBranchNotFoundException extends NotFoundException {
    public LibraryBranchNotFoundException(final String branchCode) {
        super(LibraryBranch.class.getSimpleName(), branchCode);
    }
}