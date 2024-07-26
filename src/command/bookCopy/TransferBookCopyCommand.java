package command.bookCopy;

import command.Command;
import service.BookService;

import java.util.List;

public class TransferBookCopyCommand implements Command {
    private final BookService _bookService;

    public TransferBookCopyCommand(BookService bookService) {
        _bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() < 2 || args.size() > 3) {
            throw new IllegalArgumentException("Usage: TRANSFER-BOOK-COPY [sourceBranchCode] [destinationBranchCode] [bookCopyId:optional]");
        }

        var sourceCode = args.get(0);
        var destinationCode = args.get(1);
        var bookCopyId = args.size() == 3 ? args.get(2) : null;

        if (bookCopyId == null) {
            _bookService.transferAllBooks(sourceCode, destinationCode);
        } else {
            _bookService.transferBookCopy(bookCopyId, sourceCode, destinationCode);
        }
    }
}
