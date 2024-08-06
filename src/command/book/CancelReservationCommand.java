package command.book;

import java.util.List;

import command.Command;
import service.BookReservationService;

public class CancelReservationCommand implements Command {
    private final BookReservationService bookReservationService;

    public CancelReservationCommand(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: CANCEL-RESERVATION [reservationId]");
        }

        final String bookReservationId = args.getFirst();

        bookReservationService.cancelBookReservation(bookReservationId);
    }
}