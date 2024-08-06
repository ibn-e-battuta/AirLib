package exception.librarybranch;

import exception.category.AlreadyExistsException;
import model.LibraryBranch;

public class LibraryBranchExistsException extends AlreadyExistsException {
    public LibraryBranchExistsException(final String branchCode) {
        super(LibraryBranch.class.getSimpleName(), branchCode);
    }
}