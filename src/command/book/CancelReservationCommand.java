package command.book;

import command.Command;
import service.ReservationService;

import java.util.List;

public class CancelReservationCommand implements Command {
    private final ReservationService _reservationService;

    public CancelReservationCommand(ReservationService reservationService) {
        _reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: CANCEL-RESERVATION [reservationId]");
        }

        var reservationId = args.get(0);

        _reservationService.cancelReservation(reservationId);
    }
}
