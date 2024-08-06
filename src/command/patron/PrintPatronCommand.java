package command.patron;

import java.util.List;

import command.Command;
import response.PatronResponse;
import service.PatronService;
import util.Logger;

public class PrintPatronCommand implements Command {
    private final PatronService patronService;
    private final Logger logger;

    public PrintPatronCommand(PatronService patronService, Logger logger) {
        this.patronService = patronService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-PATRON [patronId]");
        }

        final String patronId = args.getFirst();

        final PatronResponse patronResponse = patronService.getPatron(patronId);
        logger.consoleOutput(patronResponse.toString());
    }
}