package command.patron;

import command.Command;
import service.ReservationService;
import util.Logger;

import java.util.List;

public class GetPatronHistoryCommand implements Command {
    private final ReservationService _reservationService;
    private final Logger _logger;

    public GetPatronHistoryCommand(ReservationService reservationService, Logger logger) {
        _reservationService = reservationService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PATRON-HISTORY [patronId]");
        }

        var patronId = args.get(0);

        var bookCheckouts = _reservationService.getPatronBorrowingHistory(patronId);
        _logger.info("Borrowing history for patron: ");
        for (var bookCheckout : bookCheckouts) {
            _logger.info(bookCheckout.toString());
        }
    }
}
