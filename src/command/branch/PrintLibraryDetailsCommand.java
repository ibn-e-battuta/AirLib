package command.branch;

import java.util.List;

import command.Command;
import model.Library;
import response.LibraryBranchResponse;
import util.Logger;

public class PrintLibraryDetailsCommand implements Command {
    private final Logger logger;

    public PrintLibraryDetailsCommand(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Usage: PRINT-LIBRARY");
        }

        final Library library = Library.getInstance();

        logger.consoleOutput(library.toString());
        library.getLibraryBranches().stream().map(LibraryBranchResponse::toString).forEach(logger::consoleOutput);
    }
}