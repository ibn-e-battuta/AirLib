package service;

import java.util.List;

import exception.BranchExistsException;
import exception.BranchNotFoundException;
import exception.NonEmptyBranchException;
import model.Branch;
import model.Library;
import util.Logger;

public class LibraryService {
    private final Library library;
    private final Logger logger;
    private static String branchCode = "LBR";
    private int branchCount = 0;

    public LibraryService(Library library, Logger logger) {
        this.library = library;
        this.logger = logger;
    }

    public void addBranch(String name, String address) throws BranchExistsException {
        var response = library.getBranchByNameAndAddress(name, address);
        if (response.isPresent()) {
            throw new BranchExistsException(name, address);
        }
        branchCount++;
        var branch = new Branch(branchCode + branchCount, name, address);
        library.addBranch(branch);
        logger.info("Added branch: " + branch.getId());
    }

    public void removeBranch(String branchId) throws BranchNotFoundException, NonEmptyBranchException {
        var branch = library.getBranch(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));
        var branchBooks = branch.getBookItems();

        if (!branchBooks.isEmpty()) {
            throw new NonEmptyBranchException(branchId, branchBooks.size());
        }

        library.removeBranch(branchId);
        logger.info("Removed branch: " + branchId);
    }

    public Branch getBranch(String branchId) throws BranchNotFoundException {
        return library.getBranch(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));
    }

    public void updateBranch(String id, String name, String address) throws BranchNotFoundException {
        var branch = library.getBranch(id).orElseThrow(() -> new BranchNotFoundException(id));
        branch.setName(name);
        branch.setAddress(address);
        library.updateBranch(branch);
        logger.info("Updated branch: " + branch.getId());
    }

    public List<Branch> getAllBranches() {
        return library.getBranches();
    }

    public Library getLibrary() {
        return library;
    }
}
