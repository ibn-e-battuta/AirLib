package command.book;

import command.Command;
import model.response.BookResponse;
import service.BookService;
import util.Logger;

import java.util.List;

public class AvailableBooksCommand implements Command {
    private final BookService _bookService;
    private final Logger _logger;

    public AvailableBooksCommand(BookService bookService, Logger logger) {
        _bookService = bookService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() > 1) {
            throw new IllegalArgumentException("Usage: AVAILABLE-BOOKS [branchId:optional]");
        }

        var branchId = args.size() == 1 ? args.get(0) : null;

        List<BookResponse> books = null;

        if (branchId == null) {
            books = _bookService.getAvailableBooks();
        } else {
            books = _bookService.getAvailableBooksByBranch(branchId);
        }

        _logger.info("Available books:");
        for (var book : books) {
            _logger.info(book.toString());
        }
    }
}
