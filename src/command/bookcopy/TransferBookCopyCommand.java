package command.bookcopy;

import java.util.List;

import command.Command;
import service.BookCopyService;

public class TransferBookCopyCommand implements Command {
    private final BookCopyService bookCopyService;

    public TransferBookCopyCommand(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() < 2 || args.size() > 3) {
            throw new IllegalArgumentException(
                    "Usage: TRANSFER-BOOK-COPY [sourceBranchCode] [destinationBranchCode] [bookCopyId:optional]");
        }

        final String sourceCode = args.get(0);
        final String destinationCode = args.get(1);
        final String bookCopyId = args.size() == 3 ? args.get(2) : null;

        if (bookCopyId == null) {
            bookCopyService.transferAllBookCopies(sourceCode, destinationCode);
        } else {
            bookCopyService.transferBookCopy(bookCopyId, sourceCode, destinationCode);
        }
    }
}