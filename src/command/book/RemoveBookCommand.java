package command.book;

import service.BookService;

import java.util.List;
import command.Command;

public class RemoveBookCommand implements Command {
    private final BookService bookService;

    public RemoveBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: DELETE-BOOK [isbn]");
        }

        String isbn = args.get(0);
        bookService.removeBook(isbn);
    }
}
