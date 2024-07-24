package command.branch;

import command.Command;
import service.LibraryService;

import java.util.List;

public class UpdateBranchCommand implements Command {
    private final LibraryService libraryService;

    public UpdateBranchCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: UPDATE-BRANCH [branchId] [name] [address]");
        }
        String branchId = args.get(0);
        String name = args.get(1);
        String address = args.get(2);
        libraryService.updateBranch(branchId, name, address);
    }
}
