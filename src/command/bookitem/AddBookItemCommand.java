package command.bookitem;

import java.util.List;

import command.Command;
import service.BookService;

public class AddBookItemCommand implements Command {
    private final BookService bookService;

    public AddBookItemCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-BOOK-ITEM [isbn] [branchId]");
        }

        String isbn = args.get(0);
        String branchId = args.get(1);

        bookService.addBookItem(isbn, branchId);
    }
}
