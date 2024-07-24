package command.bookitem;

import java.util.List;

import command.Command;
import service.ReservationService;

public class CheckoutBookCommand implements Command {
    private final ReservationService reservationService;

    public CheckoutBookCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: CHECKOUT-BOOK [patronId] [bookItemId] [branchId]");
        }

        String patronId = args.get(0);
        String bookItemId = args.get(1);
        String branchId = args.get(2);

        reservationService.checkoutBook(patronId, bookItemId, branchId);
    }
}