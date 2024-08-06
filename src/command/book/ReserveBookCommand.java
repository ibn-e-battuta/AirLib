package command.book;

import java.util.List;

import command.Command;
import service.BookReservationService;

public class ReserveBookCommand implements Command {
    private final BookReservationService bookReservationService;

    public ReserveBookCommand(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: RESERVE-BOOK [patronId] [isbn]");
        }

        final String patronId = args.get(0);
        final String isbn = args.get(1);

        bookReservationService.addBookReservation(patronId, isbn);
    }
}