package model;

import model.response.LibraryBranchResponse;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance = null;
    private String name;
    private List<LibraryBranch> branches;

    private Library() {
        this.branches = new ArrayList<>();
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

    public List<LibraryBranchResponse> getBranches() {
        return branches.stream().map(b -> new LibraryBranchResponse(b.getId(), b.getName(), b.getAddress(), b.getBookItems().size())).toList();
    }

    public void addBranch(LibraryBranch branch) {
        branches.add(branch);
    }

    @Override
    public String toString() {
        return "Library [ " +
                "Name='" + name + '\'' +
                " ]";
    }
}
