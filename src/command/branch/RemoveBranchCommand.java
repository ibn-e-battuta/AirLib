package command.branch;

import command.Command;
import service.LibraryService;

import java.util.List;

public class RemoveBranchCommand implements Command {
    private final LibraryService _libraryService;

    public RemoveBranchCommand(LibraryService libraryService) {
        _libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: REMOVE-BRANCH [branchCode]");
        }

        var branchCode = args.get(0);
        _libraryService.removeBranch(branchCode);
    }
}
