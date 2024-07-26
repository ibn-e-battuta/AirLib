package command.branch;

import command.Command;
import service.LibraryService;
import util.Logger;

import java.util.List;

public class PrintBranchCommand implements Command {
    private final LibraryService _libraryService;
    private final Logger _logger;

    public PrintBranchCommand(LibraryService libraryService, Logger logger) {
        _libraryService = libraryService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.isEmpty()) {
            var branches = _libraryService.getAllBranches();
            for (int i = 0; i < branches.size(); i++) {
                _logger.info(branches.get(i).toString());
                if (i != branches.size() - 1)
                    System.out.println();
            }
        } else if (args.size() == 1) {
            String branchId = args.get(0);
            var branch = _libraryService.getBranch(branchId);
            _logger.info(branch.toString());
        } else {
            throw new IllegalArgumentException("Usage: PRINT-BRANCH [branchId]");
        }
    }
}
