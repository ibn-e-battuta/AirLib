package command.branch;

import java.util.List;

import command.Command;
import service.LibraryService;

public class RemoveBranchCommand implements Command {
    private final LibraryService libraryService;

    public RemoveBranchCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: REMOVE-BRANCH [branchId]");
        }
        String branchId = args.get(0);
        libraryService.removeBranch(branchId);
    }
}
