package command.book;

import java.util.List;

import command.Command;
import response.BookResponse;
import service.BookCopyService;
import util.Logger;

public class PrintBorrowedBooksCommand implements Command {
    private final BookCopyService bookCopyService;
    private final Logger logger;

    public PrintBorrowedBooksCommand(BookCopyService bookCopyService, Logger logger) {
        this.bookCopyService = bookCopyService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() > 1) {
            throw new IllegalArgumentException("Usage: BORROWED-BOOKS [branchCode:optional]");
        }

        final String branchCode = args.size() == 1 ? args.getFirst() : null;

        List<BookResponse> bookResponses = null;

        if (branchCode == null) {
            bookResponses = bookCopyService.getBooksByAvailability( false);
        } else {
            bookResponses = bookCopyService.getBooksByBranchAndAvailability(branchCode, false);
        }

        logger.info("Borrowed books:");
        bookResponses.stream().map(BookResponse::toString).forEach(logger::info);
    }
}