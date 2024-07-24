package command.book;

import java.util.List;

import command.Command;
import service.BookService;

public class UpdateBookCommand implements Command {
    private final BookService bookService;

    public UpdateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 5) {
            throw new IllegalArgumentException("Usage: UPDATE-BOOK [isbn] [title] [author] [subject] [publicationYear]");
        }

        String isbn = args.get(0);
        String title = args.get(1);
        String author = args.get(2);
        String subject = args.get(3);
        Integer publicationDate = Integer.valueOf(args.get(4));

        bookService.updateBook(isbn, title, author, subject, publicationDate);
    }
}

