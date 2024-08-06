package command.bookcopy;

import java.util.List;

import command.Command;
import service.BookCheckoutService;

public class RenewBookCopyCommand implements Command {
    private final BookCheckoutService bookCheckoutService;

    public RenewBookCopyCommand(BookCheckoutService reservationService) {
        bookCheckoutService = reservationService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: RENEW-BOOK-COPY [reservationId]");
        }

        final String bookReservationId = args.getFirst();

        bookCheckoutService.renewBook(bookReservationId);
    }
}