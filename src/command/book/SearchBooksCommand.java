package command.book;

import java.util.List;

import command.Command;
import response.BookResponse;
import service.BookService;
import util.Logger;

public class SearchBooksCommand implements Command {
    private final BookService bookService;
    private final Logger logger;

    public SearchBooksCommand(BookService bookService, Logger logger) {
        this.bookService = bookService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: SEARCH-BOOKS [searchBy] [query]");
        }

        final String searchBy = args.get(0);
        final String query = args.get(1);

        final List<BookResponse> bookResponses = bookService.searchBooks(query, searchBy);
        if (bookResponses.isEmpty()) {
            logger.console("No books found matching the search criteria.");
        } else {
            logger.console("Search results:");
            bookResponses.forEach(b -> logger.console(b.toString()));
        }
    }
}