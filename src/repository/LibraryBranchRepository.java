package repository;

import model.LibraryBranch;
import model.response.LibraryBranchResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LibraryBranchRepository {
    private final Map<String, LibraryBranch> branches = new HashMap<>();

    public void addBranch(LibraryBranch branch) {
        branches.put(branch.getId(), branch);
    }

    public void updateBranch(LibraryBranch branch) {
        branches.put(branch.getId(), branch);
    }

    public Optional<LibraryBranch> getBranch(String branchCode) {
        return branches.values().stream().filter(b -> b.getId().equals(branchCode) && !b.isDeleted()).findAny();
    }

    public void removeBranch(String branchCode) {
        var branch = branches.get(branchCode);
        branch.setDeleted(true);
    }

    public List<LibraryBranchResponse> getBranches() {
        return branches.values().stream().map(b -> new LibraryBranchResponse(b.getId(), b.getName(), b.getAddress(), b.getBookItems().size())).toList();
    }
}
