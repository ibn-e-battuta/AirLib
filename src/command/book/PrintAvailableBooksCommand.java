package command.book;

import java.util.List;

import command.Command;
import response.BookResponse;
import service.BookCopyService;
import util.Logger;

public class PrintAvailableBooksCommand implements Command {
    private final BookCopyService bookCopyService;
    private final Logger logger;

    public PrintAvailableBooksCommand(BookCopyService bookCopyService, Logger logger) {
        this.bookCopyService = bookCopyService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() > 1) {
            throw new IllegalArgumentException("Usage: AVAILABLE-BOOKS [branchCode:optional]");
        }

        final String branchCode = args.size() == 1 ? args.getFirst() : null;

        List<BookResponse> bookResponses = null;

        if (branchCode == null) {
            bookResponses = bookCopyService.getBooksByAvailability(true);
        } else {
            bookResponses = bookCopyService.getBooksByBranchAndAvailability(branchCode, true);
        }

        logger.info("Available books:");
        bookResponses.stream().map(BookResponse::toString).forEach(logger::info);
    }
}