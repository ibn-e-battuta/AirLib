package command.bookCopy;

import command.Command;
import service.ReservationService;

import java.util.List;

public class ReturnBookCopyCommand implements Command {
    private final ReservationService _reservationService;

    public ReturnBookCopyCommand(ReservationService reservationService) {
        _reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: RETURN-BOOK-COPY [reservationId]");
        }

        var reservationId = args.get(0);

        _reservationService.returnBook(reservationId);
    }
}
