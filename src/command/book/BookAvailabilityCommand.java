package command.book;

import command.Command;
import service.BookService;
import util.Logger;

import java.util.List;

public class BookAvailabilityCommand implements Command {

    private final BookService _bookService;
    private final Logger _logger;

    public BookAvailabilityCommand(BookService bookService, Logger logger) {
        _bookService = bookService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: BOOK-AVAILABILITY [isbn]");
        }

        var isbn = args.get(0);

        var bookAvailabilityResponse = _bookService.getBookAvailability(isbn);

        _logger.info(bookAvailabilityResponse.toString());
    }
}
