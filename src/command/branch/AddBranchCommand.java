package command.branch;

import command.Command;
import command.CommandInputProcessor;
import service.LibraryService;

import java.util.List;


public class AddBranchCommand implements Command {
    private final LibraryService _libraryService;

    public AddBranchCommand(LibraryService libraryService) {
        _libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: ADD-BRANCH [branchCode] [name] [address]");
        }

        var branchCode = args.get(0);
        var name = CommandInputProcessor.processToken(args.get(1));
        var address = CommandInputProcessor.processToken(args.get(2));

        _libraryService.addBranch(branchCode, name, address);
    }
}