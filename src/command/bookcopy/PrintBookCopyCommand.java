package command.bookcopy;

import java.util.List;

import command.Command;
import response.BookCopyResponse;
import service.BookCopyService;
import util.Logger;

public class PrintBookCopyCommand implements Command {
    private final BookCopyService bookCopyService;
    private final Logger logger;

    public PrintBookCopyCommand(BookCopyService bookCopyService, Logger logger) {
        this.bookCopyService = bookCopyService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-BOOK-COPY [bookCopyId]");
        }

        final String bookCopyId = args.getFirst();
        final BookCopyResponse bookCopyResponse = bookCopyService.printBookCopy(bookCopyId);

        logger.info(bookCopyResponse.toString());
    }
}