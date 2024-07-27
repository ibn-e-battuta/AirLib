package service;

import java.util.List;
import java.util.Optional;

import exception.librarybranch.LibraryBranchExistsException;
import exception.librarybranch.LibraryBranchNotEmptyException;
import exception.librarybranch.LibraryBranchNotFoundException;
import model.BookCopy;
import model.Library;
import model.LibraryBranch;
import repository.contract.ILibraryBranchRepository;
import response.LibraryBranchResponse;
import util.Logger;

public class LibraryBranchService {
    private final ILibraryBranchRepository libraryBranchRepository;
    private final Logger logger;

    public LibraryBranchService(ILibraryBranchRepository libraryBranchRepository, Logger logger) {
        this.libraryBranchRepository = libraryBranchRepository;
        this.logger = logger;
    }

    public void addLibraryBranch(final String branchCode, final String name, final String address) {
        final Optional<LibraryBranch> optionalLibraryBranch = libraryBranchRepository.getById(branchCode);
        if (optionalLibraryBranch.isPresent()) {
            throw new LibraryBranchExistsException(branchCode);
        }

        LibraryBranch libraryBranch = new LibraryBranch(branchCode, name, address);
        Library.getInstance().addLibraryBranch(libraryBranch);
        libraryBranchRepository.add(libraryBranch);
        logger.consoleOutput("Added library branch: " + libraryBranch.getId());
    }

    public void removeLibraryBranch(final String branchCode) {
        final LibraryBranch libraryBranch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));
        final List<BookCopy> bookCopies = libraryBranch.getBookCopies();

        if (!bookCopies.isEmpty()) {
            throw new LibraryBranchNotEmptyException(branchCode, bookCopies.size());
        }

        libraryBranchRepository.remove(branchCode);
        logger.consoleOutput("Removed library branch: " + libraryBranch.getId());
    }

    public LibraryBranchResponse getLibraryBranch(final String branchCode) {
        final LibraryBranch libraryBranch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));
        return new LibraryBranchResponse(libraryBranch.getId(), libraryBranch.getName(), libraryBranch.getAddress(),
                libraryBranch.getBookCopies().size());
    }

    public void updateLibraryBranch(final String branchCode, final String name, final String address) {
        final LibraryBranch libraryBranch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));

        libraryBranch.setName(name);
        libraryBranch.setAddress(address);

        libraryBranchRepository.update(libraryBranch);
        logger.consoleOutput("Updated library branch: " + libraryBranch.getId());
    }

    public List<LibraryBranchResponse> getAllLibraryBranches() {
        final List<LibraryBranch> libraryBranches = libraryBranchRepository.getAll();
        return libraryBranches.stream().map(b-> new LibraryBranchResponse(b.getId(), b.getName(), b.getAddress(), b.getBookCopies().size())).toList();
    }
}