package command.bookcopy;

import java.util.List;

import command.Command;
import service.BookCopyService;

public class AddBookCopyCommand implements Command {
    private final BookCopyService bookCopyService;

    public AddBookCopyCommand(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-BOOK-COPY [isbn] [branchCode]");
        }

        final String isbn = args.get(0);
        final String branchCode = args.get(1);

        bookCopyService.addBookCopy(isbn, branchCode);
    }
}