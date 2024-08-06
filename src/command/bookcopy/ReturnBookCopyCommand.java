package command.bookcopy;

import java.util.List;

import command.Command;
import service.BookCheckoutService;

public class ReturnBookCopyCommand implements Command {
    private final BookCheckoutService bookCheckoutService;

    public ReturnBookCopyCommand(BookCheckoutService reservationService) {
        bookCheckoutService = reservationService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: RETURN-BOOK-COPY [reservationId]");
        }

        final String bookReservationId = args.getFirst();

        bookCheckoutService.returnBook(bookReservationId);
    }
}