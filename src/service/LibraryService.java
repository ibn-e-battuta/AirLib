package service;

import exception.BranchExistsException;
import exception.BranchNotFoundException;
import exception.NonEmptyBranchException;
import model.Library;
import model.LibraryBranch;
import model.response.LibraryBranchResponse;
import repository.LibraryBranchRepository;
import util.Logger;

import java.util.List;

public class LibraryService {
    private final LibraryBranchRepository _libraryBranchRepository;
    private final Logger _logger;

    public LibraryService(LibraryBranchRepository libraryBranchRepository, Logger logger) {
        _libraryBranchRepository = libraryBranchRepository;
        _logger = logger;
    }

    public void addBranch(String branchCode, String name, String address) throws BranchExistsException {
        var response = _libraryBranchRepository.getBranch(branchCode);
        if (response.isPresent()) {
            throw new BranchExistsException(branchCode);
        }

        var branch = new LibraryBranch(branchCode, name, address);
        Library.getInstance().addBranch(branch);
        _libraryBranchRepository.addBranch(branch);
        _logger.info("Added branch: " + branch.getId());
    }

    public void removeBranch(String branchCode) throws BranchNotFoundException, NonEmptyBranchException {
        var branch = _libraryBranchRepository.getBranch(branchCode).orElseThrow(() -> new BranchNotFoundException(branchCode));
        var branchBooks = branch.getBookItems();

        if (!branchBooks.isEmpty()) {
            throw new NonEmptyBranchException(branchCode, branchBooks.size());
        }

        _libraryBranchRepository.removeBranch(branchCode);
        _logger.info("Removed branch: " + branch.getId());
    }

    public LibraryBranchResponse getBranch(String branchId) throws BranchNotFoundException {
        var branch = _libraryBranchRepository.getBranch(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));
        return new LibraryBranchResponse(branch.getId(), branch.getName(), branch.getAddress(), branch.getBookItems().size());
    }

    public void updateBranch(String branchCode, String name, String address) throws BranchNotFoundException {
        var branch = _libraryBranchRepository.getBranch(branchCode).orElseThrow(() -> new BranchNotFoundException(branchCode));
        branch.setName(name);
        branch.setAddress(address);
        _libraryBranchRepository.updateBranch(branch);
        _logger.info("Updated branch: " + branch.getId());
    }

    public List<LibraryBranchResponse> getAllBranches() {
        return _libraryBranchRepository.getBranches();
    }
}
