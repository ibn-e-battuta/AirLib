package command.branch;

import command.Command;
import model.Library;
import util.Logger;

import java.util.List;

public class PrintLibraryDetailsCommand implements Command {
    private final Logger _logger;

    public PrintLibraryDetailsCommand(Logger logger) {
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Usage: PRINT-LIBRARY");
        }
        var library = Library.getInstance();
        _logger.info(library.toString());
        for (var branchResponse : library.getBranches()) {
            _logger.info(branchResponse.toString());
        }
    }
}
