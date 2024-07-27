package command.book;

import java.util.List;

import command.Command;
import command.CommandInputProcessor;
import service.BookService;

public class UpdateBookCommand implements Command {
    private final BookService bookService;

    public UpdateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 5) {
            throw new IllegalArgumentException("Usage: UPDATE-BOOK [isbn] [title] [authors] [genres] [publicationYear]");
        }

        final String isbn = args.get(0);
        final String title = CommandInputProcessor.processToken(args.get(1));
        final List<String> authors = CommandInputProcessor.processListToken(args.get(2));
        final List<String> genres = CommandInputProcessor.processListToken(args.get(3));
        final int publicationDate = Integer.parseInt(args.get(4));

        bookService.updateBook(isbn, title, authors, genres, publicationDate);
    }
}