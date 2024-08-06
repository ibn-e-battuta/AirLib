package command.branch;

import java.util.List;

import command.Command;
import response.LibraryBranchResponse;
import service.LibraryBranchService;
import util.Logger;

public class PrintBranchCommand implements Command {
    private final LibraryBranchService libraryBranchService;
    private final Logger logger;

    public PrintBranchCommand(LibraryBranchService libraryBranchService, Logger logger) {
        this.libraryBranchService = libraryBranchService;
        this.logger = logger;
    }

    @Override
    public void execute(List<String> args) {
        if (args.isEmpty()) {
            final List<LibraryBranchResponse> libraryBranchResponses = libraryBranchService.getAllLibraryBranches();
            libraryBranchResponses.stream().map(LibraryBranchResponse::toString).forEach(logger::consoleOutput);
        } else if (args.size() == 1) {
            final String branchCode = args.getFirst();
            final LibraryBranchResponse libraryBranchResponse = libraryBranchService.getLibraryBranch(branchCode);
            logger.consoleOutput(libraryBranchResponse.toString());
        } else {
            throw new IllegalArgumentException("Usage: PRINT-BRANCH [branchCode]");
        }
    }
}