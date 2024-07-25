package command.bookCopy;

import java.util.List;

import command.Command;
import service.BookService;

public class AddBookCopyCommand implements Command {
    private final BookService _bookService;

    public AddBookCopyCommand(BookService bookService) {
        _bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-BOOK-COPY [isbn] [branchId]");
        }

        var isbn = args.get(0);
        var branchId = args.get(1);

        _bookService.addBookCopy(isbn, branchId);
    }
}
