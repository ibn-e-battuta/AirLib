package command.patron;

import command.Command;
import service.PatronService;
import util.Logger;

import java.util.List;

public class PrintPatronCommand implements Command {

    private final PatronService _patronService;
    private final Logger _logger;

    public PrintPatronCommand(PatronService patronService, Logger logger) {
        _patronService = patronService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-PATRON [patronId]");
        }

        var patronId = args.get(0);

        var patronResponse = _patronService.getPatron(patronId);
        _logger.info(patronResponse.toString());
    }
}
