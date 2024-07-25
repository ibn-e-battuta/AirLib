package command.bookCopy;

import command.Command;
import service.BookService;
import util.Logger;

import java.util.List;

public class PrintBookCopyCommand implements Command {
    private final BookService _bookService;
    private final Logger _logger;

    public PrintBookCopyCommand(BookService bookService, Logger logger) {
        _bookService = bookService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-BOOK-COPY [bookCopyId]");
        }

        var bookCopyId = args.get(0);
        var bookCopyResponse = _bookService.printBookCopy(bookCopyId);

        _logger.info(bookCopyResponse.toString());
    }
}
