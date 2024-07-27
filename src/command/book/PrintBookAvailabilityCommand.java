package command.book;

import java.util.List;

import command.Command;
import response.BookAvailabilityResponse;
import service.BookService;
import util.Logger;

public class PrintBookAvailabilityCommand implements Command {

    private final BookService bookService;
    private final Logger logger;

    public PrintBookAvailabilityCommand(BookService bookService, Logger logger) {
        this.bookService = bookService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: BOOK-AVAILABILITY [isbn]");
        }

        final String isbn = args.getFirst();

        BookAvailabilityResponse bookAvailabilityResponse = bookService.getBookAvailability(isbn);
        logger.consoleOutput(bookAvailabilityResponse.toString());
    }
}