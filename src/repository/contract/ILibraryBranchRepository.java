package repository.contract;

import java.util.List;

import model.LibraryBranch;
import repository.contract.base.IBaseRepository;

public interface ILibraryBranchRepository extends IBaseRepository<LibraryBranch, String> {
    void remove(final String branchCode);
    List<LibraryBranch> getAll();
}
