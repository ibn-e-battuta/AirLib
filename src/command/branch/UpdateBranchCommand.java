package command.branch;

import command.Command;
import command.CommandInputProcessor;
import service.LibraryService;

import java.util.List;

public class UpdateBranchCommand implements Command {
    private final LibraryService _libraryService;

    public UpdateBranchCommand(LibraryService libraryService) {
        _libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: UPDATE-BRANCH [branchCode] [name] [address]");
        }

        var branchCode = args.get(0);
        var name = CommandInputProcessor.processToken(args.get(1));
        var address = CommandInputProcessor.processToken(args.get(2));

        _libraryService.updateBranch(branchCode, name, address);
    }
}
