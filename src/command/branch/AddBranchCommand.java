package command.branch;

import java.util.List;

import command.Command;
import command.CommandInputProcessor;
import service.LibraryBranchService;

public class AddBranchCommand implements Command {
    private final LibraryBranchService libraryBranchService;

    public AddBranchCommand(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: ADD-BRANCH [branchCode] [name] [address]");
        }

        final String branchCode = args.get(0);
        final String name = CommandInputProcessor.processToken(args.get(1));
        final String address = CommandInputProcessor.processToken(args.get(2));

        libraryBranchService.addLibraryBranch(branchCode, name, address);
    }
}