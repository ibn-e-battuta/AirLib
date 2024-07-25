package command.book;

import command.Command;
import service.ReservationService;

import java.util.List;

public class ReserveBookCommand implements Command {
    private final ReservationService _reservationService;

    public ReserveBookCommand(ReservationService reservationService) {
        _reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: RESERVE-BOOK [patronId] [isbn]");
        }

        var patronId = args.get(0);
        var isbn = args.get(1);

        _reservationService.reserveBook(patronId, isbn);
    }
}
