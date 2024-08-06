package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.LibraryBranch;
import repository.contract.ILibraryBranchRepository;

public class LibraryBranchRepository implements ILibraryBranchRepository {
    private final Map<String, LibraryBranch> branches = new HashMap<>();

    public Optional<LibraryBranch> getById(final String branchCode) {
        return branches.values().stream().filter(b -> b.getId().equals(branchCode) && !b.isDeleted()).findAny();
    }

    public void add(final LibraryBranch branch) {
        branches.put(branch.getId(), branch);
    }

    public void update(final LibraryBranch branch) {
        branches.put(branch.getId(), branch);
    }

    public void remove(final String branchCode) {
        var branch = branches.get(branchCode);
        branch.setDeleted(true);
    }

    public List<LibraryBranch> getAll() {
        return branches.values().stream().filter(b -> !b.isDeleted()).toList();
    }
}