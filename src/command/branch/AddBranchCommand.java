package command.branch;

import command.Command;
import service.LibraryService;

import java.util.List;


public class AddBranchCommand implements Command {
    private final LibraryService libraryService;

    public AddBranchCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-BRANCH [name] [address]");
        }
        String name = args.get(0);
        String address = args.get(1);
        libraryService.addBranch(name, address);
    }
}