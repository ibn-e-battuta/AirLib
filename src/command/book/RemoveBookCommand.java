package command.book;

import command.Command;
import service.BookService;

import java.util.List;

public class RemoveBookCommand implements Command {
    private final BookService _bookService;

    public RemoveBookCommand(BookService bookService) {
        _bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: REMOVE-BOOK [isbn]");
        }

        var isbn = args.get(0);

        _bookService.removeBook(isbn);
    }
}
