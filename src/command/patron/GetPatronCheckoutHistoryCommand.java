package command.patron;

import command.Command;
import response.BookCheckoutResponse;
import service.PatronService;
import util.Logger;

import java.util.List;

public class GetPatronCheckoutHistoryCommand implements Command {
    private final PatronService patronService;
    private final Logger logger;

    public GetPatronCheckoutHistoryCommand(PatronService patronService, Logger logger) {
        this.patronService = patronService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PATRON-HISTORY [patronId]");
        }

        final String patronId = args.getFirst();

        final List<BookCheckoutResponse> bookCheckoutResponses = patronService.getPatronBookCheckouts(patronId);

        if (bookCheckoutResponses.isEmpty()) {
            logger.consoleOutput("No borrowing history found for patron: " + patronId);
        } else {
            logger.consoleOutput("Borrowing history for patron: ");
            bookCheckoutResponses.stream().map(BookCheckoutResponse::toString).forEach(logger::consoleOutput);
        }
    }
}