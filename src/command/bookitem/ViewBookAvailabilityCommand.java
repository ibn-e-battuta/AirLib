package command.bookitem;

import service.BookService;

import java.util.List;
import java.util.Map;

import command.Command;

public class ViewBookAvailabilityCommand implements Command {
    private final BookService bookService;

    public ViewBookAvailabilityCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: VIEW-BOOK-AVAILABILITY <isbn>");
        }

        String isbn = args.get(0);
        Map<String, Integer> availability = bookService.getBookCopiesByBranch(isbn);
        System.out.println("Book availability by branch:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println("Branch " + entry.getKey() + ": " + entry.getValue() + " copies");
        }
    }
}
