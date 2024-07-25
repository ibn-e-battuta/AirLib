package command.bookCopy;

import java.util.List;

import command.Command;
import service.ReservationService;

public class CheckoutBookCopyCommand implements Command {
    private final ReservationService _reservationService;

    public CheckoutBookCopyCommand(ReservationService reservationService) {
        this._reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: CHECKOUT-BOOK-COPY [patronId] [bookCopyId] [branchId]");
        }

        var patronId = args.get(0);
        var bookCopyId = args.get(1);
        var branchId = args.get(2);

        _reservationService.checkoutBook(patronId, bookCopyId, branchId);
    }
}