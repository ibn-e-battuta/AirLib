package command.bookitem;

import java.util.List;

import command.Command;
import model.BookItemResponse;
import service.BookService;

public class PrintBookItemCommand implements Command {
    private final BookService bookService;

    public PrintBookItemCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-BOOK-ITEM [bookItemId]");
        }

        var bookItemId = args.get(0);
        var bookItem = bookService.getBookItem(bookItemId);
        
        System.out.println(new BookItemResponse(bookItem.getId(), bookItem.getBook().getTitle(), bookItem.getBook().getAuthor(), bookItem.getBook().getIsbn(), bookItem.getBranch().getName(), bookItem.isAvailable()));
    }
}
