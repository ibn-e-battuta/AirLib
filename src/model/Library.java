package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.BranchNotFoundException;

public class Library {
    private String name;
    private final List<Branch> branches;

    public Library(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public void removeBranch(String branchId) throws BranchNotFoundException {
        var branch = branches.stream()
                .filter(b -> b.getId().equals(branchId))
                .findAny()
                .orElseThrow(() -> new BranchNotFoundException(branchId));
        branches.remove(branch);
    }

    public Optional<Branch> getBranch(String branchId) {
        return branches.stream()
                .filter(b -> b.getId().equals(branchId))
                .findAny();
    }

    public void updateBranch(Branch updatedBranch) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).getId().equals(updatedBranch.getId())) {
                branches.set(i, updatedBranch);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Library [ " +
                "Name='" + name + '\'' +
                " ]";
    }

    public Optional<Branch> getBranchByNameAndAddress(String name, String address) {
        return branches.stream()
                .filter(b -> b.getAddress().equals(address) && b.getName().equals(name))
                .findAny();
    }
}
