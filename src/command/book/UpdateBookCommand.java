package command.book;

import command.Command;
import command.CommandInputProcessor;
import service.BookService;
import util.Logger;

import java.util.List;

public class UpdateBookCommand implements Command {
    private final BookService _bookService;

    public UpdateBookCommand(BookService bookService) {
        _bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 5) {
            throw new IllegalArgumentException("Usage: UPDATE-BOOK [isbn] [title] [authors] [genres] [publicationYear]");
        }

        var isbn = args.get(0);
        var title = CommandInputProcessor.processToken(args.get(1));
        var authors = CommandInputProcessor.processListToken(args.get(2));
        var genres = CommandInputProcessor.processListToken(args.get(3));
        var publicationDate = Integer.valueOf(args.get(4));

        _bookService.updateBook(isbn, title, authors, genres, publicationDate);
    }
}

