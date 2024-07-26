package command.book;

import command.Command;
import model.response.BookResponse;
import service.BookService;
import util.Logger;

import java.util.List;

public class BorrowedBooksCommand implements Command {
    private final BookService _bookService;
    private final Logger _logger;

    public BorrowedBooksCommand(BookService bookService, Logger logger) {
        _bookService = bookService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() > 1) {
            throw new IllegalArgumentException("Usage: BORROWED-BOOKS [branchId:optional]");
        }

        var branchId = args.size() == 1 ? args.get(0) : null;

        List<BookResponse> books = null;

        if (branchId == null) {
            books = _bookService.getBorrowedBooks();
        } else {
            books = _bookService.getBorrowedBooksByBranch(branchId);
        }

        _logger.info("Borrowed books:");
        for (var book : books) {
            _logger.info(book.toString());
        }
    }
}
