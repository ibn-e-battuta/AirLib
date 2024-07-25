package command.bookCopy;

import command.Command;
import service.ReservationService;

import java.util.List;

public class CheckoutBookCopyCommand implements Command {
    private final ReservationService _reservationService;

    public CheckoutBookCopyCommand(ReservationService reservationService) {
        this._reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: CHECKOUT-BOOK-COPY [patronId] [bookCopyId] [branchCode]");
        }

        var patronId = args.get(0);
        var bookCopyId = args.get(1);
        var branchCode = args.get(2);

        _reservationService.checkoutBook(patronId, bookCopyId, branchCode);
    }
}