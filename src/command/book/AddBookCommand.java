package command.book;

import java.util.List;

import command.Command;
import service.BookService;

public class AddBookCommand implements Command {
    private final BookService bookService;

    public AddBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 5) {
            throw new IllegalArgumentException("Usage: ADD-BOOK [isbn] [title] [author] [subject] [publicationYear]");
        }

        String isbn = args.get(0);
        String title = args.get(1);
        String author = args.get(2);
        String subject = args.get(3);
        Integer publicationDate = Integer.valueOf(args.get(4));

        bookService.addBook(isbn, title, author, subject, publicationDate);
    }
}