package command.branch;

import java.util.List;

import command.Command;
import command.CommandInputProcessor;
import service.LibraryBranchService;

public class UpdateBranchCommand implements Command {
    private final LibraryBranchService libraryBranchService;

    public UpdateBranchCommand(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: UPDATE-BRANCH [branchCode] [name] [address]");
        }

        final String branchCode = args.get(0);
        final String name = CommandInputProcessor.processToken(args.get(1));
        final String address = CommandInputProcessor.processToken(args.get(2));

        libraryBranchService.updateLibraryBranch(branchCode, name, address);
    }
}