package command.bookitem;

import service.ReservationService;

import java.util.List;

import command.Command;

public class RenewBookCommand implements Command {
    private final ReservationService reservationService;

    public RenewBookCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: RENEW-BOOK <reservationId>");
        }

        var reservationId = args.get(0);
        reservationService.renewBook(reservationId);
    }
}
