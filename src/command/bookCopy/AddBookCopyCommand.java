package command.bookCopy;

import command.Command;
import service.BookService;

import java.util.List;

public class AddBookCopyCommand implements Command {
    private final BookService _bookService;

    public AddBookCopyCommand(BookService bookService) {
        _bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-BOOK-COPY [isbn] [branchCode]");
        }

        var isbn = args.get(0);
        var branchCode = args.get(1);

        _bookService.addBookCopy(isbn, branchCode);
    }
}
