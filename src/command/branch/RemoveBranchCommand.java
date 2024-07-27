package command.branch;

import java.util.List;

import command.Command;
import service.LibraryBranchService;

public class RemoveBranchCommand implements Command {
    private final LibraryBranchService libraryBranchService;

    public RemoveBranchCommand(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: REMOVE-BRANCH [branchCode]");
        }

        final String branchCode = args.getFirst();
        libraryBranchService.removeLibraryBranch(branchCode);
    }
}