package command.bookitem;

import service.BookService;
import service.PatronService;
import service.ReservationService;

import java.util.List;

import command.Command;

public class ReserveBookCommand implements Command {
    private final ReservationService reservationService;
    private final PatronService patronService;
    private final BookService bookService;

    public ReserveBookCommand(ReservationService reservationService, PatronService patronService, BookService bookService) {
        this.reservationService = reservationService;
        this.patronService = patronService;
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: RESERVE-BOOK <patronId> <isbn>");
        }


    }
}
