package command.book;

import command.Command;
import service.BookService;
import util.Logger;

import java.util.List;

public class SearchBooksCommand implements Command {
    private final BookService _bookService;
    private final Logger _logger;

    public SearchBooksCommand(BookService bookService, Logger logger) {
        _bookService = bookService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: SEARCH-BOOKS [searchBy] [query]");
        }

        var searchBy = args.get(0);
        var query = args.get(1);

        var results = _bookService.searchBooks(query, searchBy);
        _logger.info("Search results:");
        for (var result : results) {
            _logger.info(result.toString());
        }
    }
}
