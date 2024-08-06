package command.book;

import java.util.List;

import command.Command;
import service.BookService;

public class RemoveBookCommand implements Command {
    private final BookService bookService;

    public RemoveBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: REMOVE-BOOK [isbn]");
        }

        final String isbn = args.getFirst();

        bookService.removeBook(isbn);
    }
}