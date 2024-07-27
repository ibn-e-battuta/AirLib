package model;

import java.util.ArrayList;
import java.util.List;

import response.LibraryBranchResponse;

public class Library {
    private static Library instance = null;
    private String name;
    private final List<LibraryBranch> libraryBranches;

    private Library() {
        this.libraryBranches = new ArrayList<>();
    }

    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void initialize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LibraryBranchResponse> getLibraryBranches() {
        return libraryBranches.stream()
                .map(b -> new LibraryBranchResponse(b.getId(), b.getName(), b.getAddress(), b.getBookCopies().size()))
                .toList();
    }

    public void addLibraryBranch(LibraryBranch branch) {
        libraryBranches.add(branch);
    }

    @Override
    public String toString() {
        return "Library [ " +
                "Name='" + name + '\'' +
                " ]";
    }
}
