package command.bookitem;

import java.util.List;

import command.Command;
import service.BookService;

public class TransferBookCommand implements Command {
    private final BookService bookService;

    public TransferBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() < 2 || args.size() > 3) {
            throw new IllegalArgumentException("Usage: TRANSFER-BOOK <sourceBranchId> <destinationBranchId> [bookItemId]");
        }

        String sourceId = args.get(0);
        String destinationId = args.get(1);
        String bookItemId = args.size() == 3 ? args.get(2) : null;

        if (bookItemId == null) {
            bookService.transferAllBooks(sourceId, destinationId);
        } else {
            bookService.transferBookItem(bookItemId, sourceId, destinationId);
        }
    }
}
